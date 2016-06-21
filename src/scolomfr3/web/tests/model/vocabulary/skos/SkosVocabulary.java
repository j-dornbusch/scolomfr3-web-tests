package scolomfr3.web.tests.model.vocabulary.skos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Selector;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import scolomfr3.web.tests.model.utils.Tree;
import scolomfr3.web.tests.model.utils.Tree.Node;
import scolomfr3.web.tests.model.utils.Triple;
import scolomfr3.web.tests.model.vocabulary.AbstractVocabulary;

public class SkosVocabulary extends AbstractVocabulary {

	@Override
	public List<Triple<String, String, String>> getCompleteContent() {
		Model model = this.getModel();

		List<Triple<String, String, String>> completeContent = new LinkedList<>();

		StmtIterator iter = model.listStatements();
		Property prefLabel = model.getProperty("http://www.w3.org/2004/02/skos/core#", "prefLabel");

		while (iter.hasNext()) {
			Statement stmt = iter.nextStatement(); // get next statement
			Resource subject = stmt.getSubject(); // get the subject
			Property predicate = stmt.getPredicate(); // get the predicate
			RDFNode object = stmt.getObject(); // get the object
			String subjectString = subject.toString();
			if (subject instanceof Resource) {
				Selector selector = new SimpleSelector(subject, prefLabel, object);
				StmtIterator stmtIterator = model.listStatements(selector);
				if (stmtIterator.hasNext()) {
					Statement statement = (Statement) stmtIterator.next();
					if (statement.getObject() instanceof Resource) {
						subjectString = statement.getObject().asResource().toString();
					} else {
						subjectString = statement.getObject().asLiteral().toString();
					}

				}
			}
			completeContent.add(Triple.create(subjectString, predicate.toString(), object.toString()));
		}
		return completeContent;
	}

	public Map<String, String> getTreeRoots() {
		Model model = this.getModel();
		Map<String, String> map = new HashMap<>();
		Property broader = model.getProperty("http://www.w3.org/2004/02/skos/core#", "broader");
		Property narrower = model.getProperty("http://www.w3.org/2004/02/skos/core#", "narrower");
		Selector broaderSelector = new SimpleSelector(null, broader, (RDFNode) null);
		Selector narrowerSelector = new SimpleSelector(null, narrower, (RDFNode) null);
		StmtIterator stmtIterator = model.listStatements(narrowerSelector);
		while (stmtIterator.hasNext()) {
			Statement statement = (Statement) stmtIterator.next();
			Resource subject = statement.getSubject();
			RDFNode object = statement.getObject();
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

	private List<Resource> getChildrenOfResource(Resource subject) {
		List<Resource> list = new ArrayList<Resource>();
		Property broader = getModel().getProperty("http://www.w3.org/2004/02/skos/core#", "narrower");
		Selector broaderSelector = new SimpleSelector(subject, broader, (RDFNode) null);
		StmtIterator stmts = getModel().listStatements(broaderSelector);
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

	@Override
	public Tree<Pair<String, String>> getTreeForUri(String uri) {
		Resource resource = getModel().getResource(uri);
		String label = getPrefLabelForResource(resource);
		Pair<String, String> rootData = new ImmutablePair<String, String>(uri, label);
		Tree<Pair<String, String>> tree = new Tree<>(rootData);
		List<Resource> children = getChildrenOfResource(resource);
		addChildrenRecursively(children, tree.getRoot());
		return tree;
	}

	private void addChildrenRecursively(List<Resource> children, Node<Pair<String, String>> parent) {
		Iterator<Resource> it = children.iterator();
		while (it.hasNext()) {
			Resource child = (Resource) it.next();
			String label = getPrefLabelForResource(child);
			Pair<String, String> data = new ImmutablePair<String, String>(child.getURI().toString(), label);
			Node<Pair<String, String>> childNode = new Node<>();
			childNode.setdata(data);
			List<Resource> grandChildren = getChildrenOfResource(child);
			addChildrenRecursively(grandChildren, childNode);
			parent.addChild(childNode);
		}

	}

	@Override
	public List<Pair<String, String>> getRevertedNarrowerRelations() {
		Property broader = getModel().getProperty("http://www.w3.org/2004/02/skos/core#", "broader");
		Property narrower = getModel().getProperty("http://www.w3.org/2004/02/skos/core#", "narrower");
		Selector broaderSelector = new SimpleSelector(null, broader, (RDFNode) null);
		List<Pair<String, String>> revertedRelations = new ArrayList<>();
		StmtIterator stmts = getModel().listStatements(broaderSelector);
		while (stmts.hasNext()) {
			Statement statement = (Statement) stmts.next();
			Selector narrowerSelector = new SimpleSelector((Resource) statement.getObject(), narrower,
					statement.getSubject());
			StmtIterator stmts2 = getModel().listStatements(narrowerSelector);
			if (stmts2.hasNext()) {
				revertedRelations.add(new ImmutablePair<String, String>(
						prefLabelWithUri((Resource) statement.getObject()), prefLabelWithUri(statement.getSubject())));
			}
		}
		return revertedRelations;
	}

	@Override
	public List<Pair<String, String>> getRevertedBroaderRelations() {
		Property broader = getModel().getProperty("http://www.w3.org/2004/02/skos/core#", "broader");
		Property narrower = getModel().getProperty("http://www.w3.org/2004/02/skos/core#", "narrower");
		Selector narrowerSelector = new SimpleSelector(null, narrower, (RDFNode) null);
		List<Pair<String, String>> revertedRelations = new ArrayList<>();
		StmtIterator stmts = getModel().listStatements(narrowerSelector);
		while (stmts.hasNext()) {
			Statement statement = (Statement) stmts.next();
			Selector broaderSelector = new SimpleSelector((Resource) statement.getObject(), broader,
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

}
