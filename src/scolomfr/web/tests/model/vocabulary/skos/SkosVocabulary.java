package scolomfr.web.tests.model.vocabulary.skos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import org.springframework.stereotype.Component;

import scolomfr.web.tests.model.utils.Tree;
import scolomfr.web.tests.model.utils.Tree.Node;
import scolomfr.web.tests.model.vocabulary.AbstractVocabulary;

@Component
public class SkosVocabulary extends AbstractVocabulary {

	private static final String SKOS_NARROWER_PROPERTY = "narrower";
	private static final String SKOS_BROADER_PROPERTY = "broader";
	public static final String SKOS_CORE = "http://www.w3.org/2004/02/skos/core#";

	/*
	 * (non-Javadoc)
	 * 
	 * @see scolomfr.web.tests.model.vocabulary.Vocabulary#getVocabRoots()
	 */
	@Override
	public Map<String, String> getVocabRoots() {
		Model model = this.getModel();
		Map<String, String> map = new TreeMap<>();

		Property member = model.getProperty(SKOS_CORE, "member");
		Selector memberSelector = new SimpleSelector(null, member, (RDFNode) null);
		StmtIterator stmtIterator = model.listStatements(memberSelector);
		while (stmtIterator.hasNext()) {
			Statement statement = stmtIterator.next();
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
	@Override
	public Map<String, String> getTreeRoots() {
		Model model = this.getModel();
		Map<String, String> map = new TreeMap<>();

		Property broader = model.getProperty(SKOS_CORE, SKOS_BROADER_PROPERTY);
		Property narrower = model.getProperty(SKOS_CORE, SKOS_NARROWER_PROPERTY);
		Selector broaderSelector = new SimpleSelector(null, broader, (RDFNode) null);
		Selector narrowerSelector = new SimpleSelector(null, narrower, (RDFNode) null);
		StmtIterator stmtIterator = model.listStatements(narrowerSelector);
		while (stmtIterator.hasNext()) {
			Statement statement = stmtIterator.next();
			Resource subject = statement.getSubject();
			if (!map.containsKey(subject.getURI())) {
				map.put(subject.getURI(), getPrefLabelForResource(subject));
			}
		}
		stmtIterator = model.listStatements(broaderSelector);
		while (stmtIterator.hasNext()) {
			Statement statement = stmtIterator.next();
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
		Property prefLabel = getModel().getProperty(SKOS_CORE, "prefLabel");
		Selector selector = new SimpleSelector(subject, prefLabel, (RDFNode) null);
		StmtIterator stmts = getModel().listStatements(selector);
		while (stmts.hasNext()) {
			Statement statement = stmts.next();
			return ((Literal) statement.getObject()).getString();
		}
		return "N.C.";
	}

	private List<Resource> getChildrenOfResource(Resource subject, boolean useMember) {
		List<Resource> list = new ArrayList<>();
		Property children = getModel().getProperty(SKOS_CORE, useMember ? "member" : SKOS_NARROWER_PROPERTY);
		Selector childrenSelector = new SimpleSelector(subject, children, (RDFNode) null);
		StmtIterator stmts = getModel().listStatements(childrenSelector);
		while (stmts.hasNext()) {
			Statement statement = stmts.next();
			list.add((Resource) statement.getObject());
		}
		Collections.sort(list, (Resource o1, Resource o2) -> o1.getURI().compareTo(o2.getURI()));
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
		Pair<String, String> rootData = new ImmutablePair<>(uri, label);
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
			Resource child = it.next();
			String label = getPrefLabelForResource(child);
			Pair<String, String> data = new ImmutablePair<>(child.getURI(), label);

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
		return getRevertedRelations(SKOS_NARROWER_PROPERTY, SKOS_BROADER_PROPERTY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see scolomfr.web.tests.model.vocabulary.Vocabulary#
	 * getRevertedBroaderRelations()
	 */
	@Override
	public List<Pair<String, String>> getRevertedBroaderRelations() {
		return getRevertedRelations(SKOS_BROADER_PROPERTY, SKOS_NARROWER_PROPERTY);
	}

	private List<Pair<String, String>> getRevertedRelations(String predicateName, String reverseName) {
		Property predicate = getModel().getProperty(SKOS_CORE, predicateName);
		Property reverse = getModel().getProperty(SKOS_CORE, reverseName);
		Selector reverseSelector = new SimpleSelector(null, reverse, (RDFNode) null);
		List<Pair<String, String>> revertedRelations = new ArrayList<>();
		StmtIterator stmts = getModel().listStatements(reverseSelector);
		while (stmts.hasNext()) {
			Statement statement = stmts.next();
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

	@Override
	public String prefLabelWithUri(Resource resource) {
		return getPrefLabelForResource(resource) + "§" + resource.getURI();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * scolomfr.web.tests.model.vocabulary.Vocabulary#getLabelsForStringPattern
	 * (java.lang.String)
	 */
	@Override
	public Map<String, String> getLabelsForStringPattern(final String pattern) {
		String newPattern = pattern.replace('(', '.').replace(')', '.');
		String queryString = "SELECT ?res ?label " + "WHERE {?res  ?property ?label" + ".FILTER regex(?label, \""
				+ newPattern + "\", \"i\")" + " FILTER (?property IN (<" + SKOS_CORE + "#prefLabel>,<" + SKOS_CORE
				+ "#altLabel>,<" + SKOS_CORE + "#scopeNote>))" + "}";
		Query query = QueryFactory.create(queryString);
		Map<String, String> map = new HashMap<>();
		try (QueryExecution qexec = QueryExecutionFactory.create(query, getModel())) {
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
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
			Statement statement = stmts.next();
			String value;
			if (statement.getObject().isLiteral()) {
				value = ((Literal) statement.getObject()).getString();
			} else {
				value = statement.getObject().toString();
			}
			if (list.containsKey(statement.getPredicate().getLocalName())) {
				value = list.get(statement.getPredicate().getLocalName()) + "||" + value;
			}
			list.put(statement.getPredicate().getLocalName(), value);
		}
		selector = new SimpleSelector(null, null, subject);
		stmts = getModel().listStatements(selector);
		while (stmts.hasNext()) {
			Statement statement = stmts.next();
			String value;
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

}