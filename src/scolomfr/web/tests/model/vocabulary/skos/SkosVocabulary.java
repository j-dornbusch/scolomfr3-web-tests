package scolomfr.web.tests.model.vocabulary.skos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Selector;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.iterator.ExtendedIterator;

import com.atlascopco.hunspell.Hunspell;

import scolomfr.web.tests.model.utils.Tree;
import scolomfr.web.tests.model.utils.Tree.Node;
import scolomfr.web.tests.model.vocabulary.AbstractVocabulary;

public class SkosVocabulary extends AbstractVocabulary {

	/*
	 * (non-Javadoc)
	 * 
	 * @see scolomfr.web.tests.model.vocabulary.Vocabulary#getVocabRoots()
	 */
	public Map<String, String> getVocabRoots() {
		Model model = this.getModel();
		Map<String, String> map = new TreeMap<>();

		Property member = model.getProperty("http://www.w3.org/2004/02/skos/core#", "member");
		Selector memberSelector = new SimpleSelector(null, member, (RDFNode) null);
		StmtIterator stmtIterator = model.listStatements(memberSelector);
		while (stmtIterator.hasNext()) {
			Statement statement = (Statement) stmtIterator.next();
			Resource subject = statement.getSubject();
			if (!map.containsKey(subject.getURI())) {
				map.put(subject.getURI(), getPrefLabelForResource(subject));
			}
		}
		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see scolomfr.web.tests.model.vocabulary.Vocabulary#getTreeRoots()
	 */
	public Map<String, String> getTreeRoots() {
		Model model = this.getModel();
		Map<String, String> map = new TreeMap<>();

		Property broader = model.getProperty("http://www.w3.org/2004/02/skos/core#", "broader");
		Property narrower = model.getProperty("http://www.w3.org/2004/02/skos/core#", "narrower");
		Selector broaderSelector = new SimpleSelector(null, broader, (RDFNode) null);
		Selector narrowerSelector = new SimpleSelector(null, narrower, (RDFNode) null);
		StmtIterator stmtIterator = model.listStatements(narrowerSelector);
		while (stmtIterator.hasNext()) {
			Statement statement = (Statement) stmtIterator.next();
			Resource subject = statement.getSubject();
			if (!map.containsKey(subject.getURI())) {
				map.put(subject.getURI(), getPrefLabelForResource(subject));
			}
		}
		stmtIterator = model.listStatements(broaderSelector);
		while (stmtIterator.hasNext()) {
			Statement statement = (Statement) stmtIterator.next();
			Resource subject = statement.getSubject();
			map.remove(subject.getURI());
		}

		return map;

	}

	/**
	 * @param subject
	 *            La ressource considérée
	 * @return Le premier preflabel trouvé (quelle que soit la langue)
	 */
	private String getPrefLabelForResource(Resource subject) {
		Property prefLabel = getModel().getProperty("http://www.w3.org/2004/02/skos/core#", "prefLabel");
		Selector selector = new SimpleSelector(subject, prefLabel, (RDFNode) null);
		StmtIterator stmts = getModel().listStatements(selector);
		while (stmts.hasNext()) {
			Statement statement = (Statement) stmts.next();
			return ((Literal) statement.getObject()).getString();
		}
		return "N.C.";
	}

	private List<Resource> getChildrenOfResource(Resource subject, boolean useMember) {
		List<Resource> list = new ArrayList<Resource>();
		Property children = getModel().getProperty("http://www.w3.org/2004/02/skos/core#",
				useMember ? "member" : "narrower");
		Selector childrenSelector = new SimpleSelector(subject, children, (RDFNode) null);
		StmtIterator stmts = getModel().listStatements(childrenSelector);
		while (stmts.hasNext()) {
			Statement statement = (Statement) stmts.next();
			list.add((Resource) statement.getObject());
		}
		Collections.sort(list, new Comparator<Resource>() {

			@Override
			public int compare(Resource o1, Resource o2) {
				return o1.getURI().compareTo(o2.getURI());

			}
		});
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * scolomfr.web.tests.model.vocabulary.Vocabulary#getTreeForUri(java.lang.
	 * String, boolean)
	 */
	@Override
	public Tree<Pair<String, String>> getTreeForUri(String uri, boolean useMember) {
		Resource resource = getModel().getResource(uri);
		String label = getPrefLabelForResource(resource);
		Pair<String, String> rootData = new ImmutablePair<String, String>(uri, label);
		Tree<Pair<String, String>> tree = new Tree<>(rootData);
		List<Resource> children = getChildrenOfResource(resource, useMember);
		addChildrenRecursively(children, tree.getRoot());
		// Si un noeud a été placé à la racine du vocabulaire en tant que member
		// et qu'on le retrouve en parcourant l'arbre,
		// on le retire de la racine
		if (useMember) {
			List<Pair<String, String>> toRemove = new ArrayList<>();
			findEntriesToRemoveRecursively(tree.getRoot().getChildren(), true, toRemove);
			Iterator<Pair<String, String>> it = toRemove.iterator();
			while (it.hasNext()) {
				tree.getRoot().removeChild(it.next());

			}
		}

		return tree;
	}

	private void findEntriesToRemoveRecursively(List<Node<Pair<String, String>>> children, Boolean firstLevel,
			List<Pair<String, String>> toRemove) {
		Iterator<Node<Pair<String, String>>> it = children.iterator();

		while (it.hasNext()) {

			Node<Pair<String, String>> node = it.next();
			if (!firstLevel) {
				toRemove.add(node.getData());
			}
			findEntriesToRemoveRecursively(node.getChildren(), false, toRemove);
		}

	}

	private void addChildrenRecursively(List<Resource> children, Node<Pair<String, String>> parent) {
		Iterator<Resource> it = children.iterator();
		while (it.hasNext()) {
			Resource child = (Resource) it.next();
			String label = getPrefLabelForResource(child);
			Pair<String, String> data = new ImmutablePair<String, String>(child.getURI().toString(), label);

			Node<Pair<String, String>> childNode = new Node<>();
			childNode.setdata(data);
			List<Resource> grandChildren = getChildrenOfResource(child, false);
			addChildrenRecursively(grandChildren, childNode);
			parent.addChild(childNode);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see scolomfr.web.tests.model.vocabulary.Vocabulary#
	 * getRevertedNarrowerRelations()
	 */
	@Override
	public List<Pair<String, String>> getRevertedNarrowerRelations() {
		return getRevertedRelations("narrower", "broader");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see scolomfr.web.tests.model.vocabulary.Vocabulary#
	 * getRevertedBroaderRelations()
	 */
	@Override
	public List<Pair<String, String>> getRevertedBroaderRelations() {
		return getRevertedRelations("broader", "narrower");
	}

	private List<Pair<String, String>> getRevertedRelations(String predicateName, String reverseName) {
		Property predicate = getModel().getProperty("http://www.w3.org/2004/02/skos/core#", predicateName);
		Property reverse = getModel().getProperty("http://www.w3.org/2004/02/skos/core#", reverseName);
		Selector reverseSelector = new SimpleSelector(null, reverse, (RDFNode) null);
		List<Pair<String, String>> revertedRelations = new ArrayList<>();
		StmtIterator stmts = getModel().listStatements(reverseSelector);
		while (stmts.hasNext()) {
			Statement statement = (Statement) stmts.next();
			Selector broaderSelector = new SimpleSelector((Resource) statement.getObject(), predicate,
					statement.getSubject());
			StmtIterator stmts2 = getModel().listStatements(broaderSelector);
			if (stmts2.hasNext()) {
				revertedRelations.add(new ImmutablePair<String, String>(
						prefLabelWithUri((Resource) statement.getObject()), prefLabelWithUri(statement.getSubject())));
			}
		}
		return revertedRelations;
	}

	private String prefLabelWithUri(Resource resource) {
		return getPrefLabelForResource(resource) + "§" + resource.getURI();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see scolomfr.web.tests.model.vocabulary.Vocabulary#
	 * getMissingNarrowerRelations()
	 */
	@Override
	public List<Pair<String, String>> getMissingNarrowerRelations() {
		Property broader = getModel().getProperty("http://www.w3.org/2004/02/skos/core#", "broader");
		Property narrower = getModel().getProperty("http://www.w3.org/2004/02/skos/core#", "narrower");
		Selector broaderSelector = new SimpleSelector(null, broader, (RDFNode) null);
		List<Pair<String, String>> missingRelations = new ArrayList<>();
		StmtIterator stmts = getModel().listStatements(broaderSelector);
		while (stmts.hasNext()) {
			Statement statement = (Statement) stmts.next();
			Selector narrowerSelector = new SimpleSelector((Resource) statement.getObject(), narrower,
					statement.getSubject());
			StmtIterator stmts2 = getModel().listStatements(narrowerSelector);
			if (!stmts2.hasNext()) {
				missingRelations.add(new ImmutablePair<String, String>(
						prefLabelWithUri((Resource) statement.getObject()), prefLabelWithUri(statement.getSubject())));
			}
		}
		return missingRelations;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see scolomfr.web.tests.model.vocabulary.Vocabulary#
	 * getMissingBroaderRelations()
	 */
	@Override
	public List<Pair<String, String>> getMissingBroaderRelations() {
		Property broader = getModel().getProperty("http://www.w3.org/2004/02/skos/core#", "broader");
		Property narrower = getModel().getProperty("http://www.w3.org/2004/02/skos/core#", "narrower");
		Selector narrowerSelector = new SimpleSelector(null, narrower, (RDFNode) null);
		List<Pair<String, String>> missingRelations = new ArrayList<>();
		StmtIterator stmts = getModel().listStatements(narrowerSelector);
		while (stmts.hasNext()) {
			Statement statement = (Statement) stmts.next();
			Selector broaderSelector = new SimpleSelector((Resource) statement.getObject(), broader,
					statement.getSubject());
			StmtIterator stmts2 = getModel().listStatements(broaderSelector);
			if (!stmts2.hasNext()) {
				missingRelations.add(new ImmutablePair<String, String>(
						prefLabelWithUri((Resource) statement.getObject()), prefLabelWithUri(statement.getSubject())));
			}
		}
		return missingRelations;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * scolomfr.web.tests.model.vocabulary.Vocabulary#getLabelsForStringPattern
	 * (java.lang.String)
	 */
	@Override
	public Map<String, String> getLabelsForStringPattern(String pattern) {
		pattern = pattern.replace('(', '.').replace(')', '.');
		String queryString = "SELECT ?res ?label " + "WHERE {?res  ?property ?label" + ".FILTER regex(?label, \""
				+ pattern + "\", \"i\")"
				+ " FILTER (?property IN (<http://www.w3.org/2004/02/skos/core#prefLabel>,<http://www.w3.org/2004/02/skos/core#altLabel>,<http://www.w3.org/2004/02/skos/core#scopeNote>))"
				+ "}";
		Query query = QueryFactory.create(queryString);
		Map<String, String> map = new HashMap<>();
		try (QueryExecution qexec = QueryExecutionFactory.create(query, getModel())) {
			ResultSet results = qexec.execSelect();
			for (; results.hasNext();) {
				QuerySolution soln = results.nextSolution();
				Literal l = soln.getLiteral("label");
				Resource r = soln.getResource("res");
				map.put(r.getURI(), l.getString());
			}
		}

		return map;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * scolomfr.web.tests.model.vocabulary.Vocabulary#getInformationForUri(java
	 * .lang.String)
	 */
	@Override
	public Map<String, String> getInformationForUri(String uri) {
		Map<String, String> list = new TreeMap<>();
		Resource subject = getModel().getResource(uri);
		Selector selector = new SimpleSelector(subject, null, (RDFNode) null);
		StmtIterator stmts = getModel().listStatements(selector);
		while (stmts.hasNext()) {
			Statement statement = (Statement) stmts.next();
			String value = "";
			if (statement.getObject().isLiteral()) {
				value = ((Literal) statement.getObject()).getString();
			} else {
				value = statement.getObject().toString();
			}
			if (list.containsKey((statement.getPredicate().getLocalName()))) {
				value = list.get((statement.getPredicate().getLocalName())) + "||" + value;
			}
			list.put(statement.getPredicate().getLocalName(), value);
		}
		selector = new SimpleSelector(null, null, subject);
		stmts = getModel().listStatements(selector);
		while (stmts.hasNext()) {
			Statement statement = (Statement) stmts.next();
			String value = "";
			if (statement.getSubject().isLiteral()) {
				value = ((Literal) statement.getSubject()).getString();
			} else {
				value = statement.getSubject().toString();
			}
			String predicateName = '|' + statement.getPredicate().getLocalName();
			if (list.containsKey(predicateName)) {
				value = list.get(predicateName) + "||" + value;
			}
			list.put(predicateName, value);
		}
		return list;
	}

	@Override
	public Map<String, List<String>> getDubiousLangStrings() {
		Pattern abbreviationsPattern = Pattern.compile("^([A-Za-zàèéêîôûù]+\\.\\s*)+([A-Za-zàèéêîôûù]+\\.*\\s*)$");

		Map<String, List<String>> dubious = new HashMap<>();
		Hunspell speller = new Hunspell("/usr/share/hunspell/fr_FR.dic", "/usr/share/hunspell/fr_FR.aff");
		Property prefLabel = getModel().getProperty("http://www.w3.org/2004/02/skos/core#", "prefLabel");
		Property altLabel = getModel().getProperty("http://www.w3.org/2004/02/skos/core#", "altLabel");
		Property scopeNote = getModel().getProperty("http://www.w3.org/2004/02/skos/core#", "scopeNote");
		Selector prefLabelSelector = new SimpleSelector(null, prefLabel, (RDFNode) null);
		Selector altLabelSelector = new SimpleSelector(null, altLabel, (RDFNode) null);
		Selector scopeNoteSelector = new SimpleSelector(null, scopeNote, (RDFNode) null);
		StmtIterator stmts1 = getModel().listStatements(prefLabelSelector);
		StmtIterator stmts2 = getModel().listStatements(altLabelSelector);
		StmtIterator stmts3 = getModel().listStatements(scopeNoteSelector);
		ExtendedIterator<Statement> stmts = stmts1.andThen(stmts2).andThen(stmts3);
		Resource vocab001 = getModel().getResource("http://data.education.fr/voc/scolomfr/scolomfr-voc-001");
		Resource vocab006 = getModel().getResource("http://data.education.fr/voc/scolomfr/scolomfr-voc-006");
		Resource vocab024 = getModel().getResource("http://data.education.fr/voc/scolomfr/scolomfr-voc-024");

		while (stmts.hasNext()) {
			Statement statement = (Statement) stmts.next();

			if (memberOfVocab(vocab001, statement.getSubject()) || memberOfVocab(vocab024, statement.getSubject())
					|| memberOfVocab(vocab006, statement.getSubject())) {
				continue;
			}
			Literal labelLit = (Literal) statement.getObject();

			if (!StringUtils.equals("fr", labelLit.getLanguage())) {
				continue;
			}
			String label = labelLit.getString();
			Matcher matcher = abbreviationsPattern.matcher(label);
			if (matcher.find()) {
				System.out.println(label + " est une abbréviation");
				continue;
			}
			label = StringUtils.replace(label, "(", " ");
			label = StringUtils.replace(label, ")", " ");
			label = StringUtils.replace(label, ",", " ");

			label = StringUtils.replace(label, "'", " ");
			label = StringUtils.replace(label, "–", " ");
			label = StringUtils.replace(label, ":", " ");
			label = StringUtils.replace(label, ";", " ");
			label = StringUtils.replace(label, "\"", " ");

			label = StringUtils.replace(label, ".", " ");
			label = StringUtils.replace(label, "…", " ");
			String[] labelFragments = label.split("\\s+");
			for (int i = 0; i < labelFragments.length; i++) {

				label = labelFragments[i];
				if (StringUtils.equals("ScoLomFr", label) || StringUtils.isAllUpperCase(label)
						|| StringUtils.isNumeric(label) || StringUtils.containsAny(label, "1234567890/")
						|| label.length() < 3 || Character.isUpperCase(label.charAt(1))) {
					continue;
				}
				if (!speller.spell(label)) {
					if (!dubious.containsKey(statement.getSubject().getURI())) {
						dubious.put(statement.getSubject().getURI(), new ArrayList<>());
					}
					if (!dubious.get(statement.getSubject().getURI()).contains(label)) {
						dubious.get(statement.getSubject().getURI()).add(label);
					}
				}
			}

		}
		speller.close();

		return dubious;
	}

	private boolean memberOfVocab(Resource vocab, Resource subject) {
		Property member = getModel().getProperty("http://www.w3.org/2004/02/skos/core#", "member");
		Selector memberSelector = new SimpleSelector(vocab, member, subject);
		StmtIterator stmtIterator = getModel().listStatements(memberSelector);
		return stmtIterator.hasNext();
	}

	@Override
	public TreeMap<String, ArrayList<String>> getMissingPrefLabels() {
		TreeMap<String, ArrayList<String>> missingPrefLabels = new TreeMap<>();
		Property prefLabel = getModel().getProperty("http://www.w3.org/2004/02/skos/core#", "prefLabel");
		Property altLabel = getModel().getProperty("http://www.w3.org/2004/02/skos/core#", "altLabel");
		Selector globalSelector = new SimpleSelector(null, null, (RDFNode) null);

		StmtIterator stmts1 = getModel().listStatements(globalSelector);

		while (stmts1.hasNext()) {

			Statement statement = (Statement) stmts1.next();
			Selector prefLabelSelector = new SimpleSelector(statement.getSubject(), prefLabel, (RDFNode) null);
			StmtIterator stmts2 = getModel().listStatements(prefLabelSelector);
			if (!stmts2.hasNext()) {
				missingPrefLabels.put(statement.getSubject().getURI(), new ArrayList<>());
				Selector altLabelSelector = new SimpleSelector(statement.getSubject(), altLabel, (RDFNode) null);
				StmtIterator stmts3 = getModel().listStatements(altLabelSelector);
				while (stmts3.hasNext()) {
					Statement altLabelStatement = (Statement) stmts3.next();
					missingPrefLabels.get(statement.getSubject().getURI())
							.add(((Literal) altLabelStatement.getObject()).getString());

				}
			}
		}
		return missingPrefLabels;
	}

}