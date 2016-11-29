package scolomfr.web.tests.model.vocabulary.skos.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Selector;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.springframework.stereotype.Component;

import scolomfr.web.tests.controller.response.Result;
import scolomfr.web.tests.model.vocabulary.Vocabulary;
import scolomfr.web.tests.model.vocabulary.algorithm.AbstractAlgorithm;
import scolomfr.web.tests.model.vocabulary.algorithm.AlgorithmNotImplementedException;
import scolomfr.web.tests.model.vocabulary.algorithm.MissingRelationDetector;

@Component
public class SkosMissingRelationDetectorImpl extends AbstractAlgorithm implements MissingRelationDetector {
	private Property broader;
	private Property narrower;

	/**
	 * Liste les relations broader qui n'ont pas pour pendant une relation
	 * narrower
	 * 
	 * @return une liste de paires uri, prefLabel
	 */
	@Override
	public Result<Map<String, List<Pair<String, String>>>> analyse(Vocabulary vocabulary)
			throws AlgorithmNotImplementedException {
		broader = vocabulary.getModel().getProperty("http://www.w3.org/2004/02/skos/core#", "broader");
		narrower = vocabulary.getModel().getProperty("http://www.w3.org/2004/02/skos/core#", "narrower");

		Map<String, List<Pair<String, String>>> missingRelations = new TreeMap<>();
		missingRelations.put(narrower.getLocalName(), getMissingNarrowerRelations(vocabulary));
		missingRelations.put(broader.getLocalName(), getMissingBroaderRelations(vocabulary));
		Result<Map<String, List<Pair<String, String>>>> result = new Result<>();
		result.setContent(missingRelations);
		result.setErrors(missingRelations.size());
		return result;
	}

	private ArrayList<Pair<String, String>> getMissingNarrowerRelations(Vocabulary vocabulary) {
		Selector broaderSelector = new SimpleSelector(null, broader, (RDFNode) null);
		ArrayList<Pair<String, String>> missingRelations = new ArrayList<>();
		StmtIterator stmts = vocabulary.getModel().listStatements(broaderSelector);
		while (stmts.hasNext()) {
			Statement statement = (Statement) stmts.next();
			Selector narrowerSelector = new SimpleSelector((Resource) statement.getObject(), narrower,
					statement.getSubject());
			StmtIterator stmts2 = vocabulary.getModel().listStatements(narrowerSelector);
			if (!stmts2.hasNext()) {
				missingRelations.add(
						new ImmutablePair<String, String>(vocabulary.prefLabelWithUri((Resource) statement.getObject()),
								vocabulary.prefLabelWithUri(statement.getSubject())));
			}
		}
		return missingRelations;
	}

	public ArrayList<Pair<String, String>> getMissingBroaderRelations(Vocabulary vocabulary) {
		Selector narrowerSelector = new SimpleSelector(null, narrower, (RDFNode) null);
		ArrayList<Pair<String, String>> missingRelations = new ArrayList<>();
		StmtIterator stmts = vocabulary.getModel().listStatements(narrowerSelector);
		while (stmts.hasNext()) {
			Statement statement = (Statement) stmts.next();
			Selector broaderSelector = new SimpleSelector((Resource) statement.getObject(), broader,
					statement.getSubject());
			StmtIterator stmts2 = vocabulary.getModel().listStatements(broaderSelector);
			if (!stmts2.hasNext()) {
				missingRelations.add(
						new ImmutablePair<String, String>(vocabulary.prefLabelWithUri((Resource) statement.getObject()),
								vocabulary.prefLabelWithUri(statement.getSubject())));
			}
		}
		return missingRelations;
	}

}
