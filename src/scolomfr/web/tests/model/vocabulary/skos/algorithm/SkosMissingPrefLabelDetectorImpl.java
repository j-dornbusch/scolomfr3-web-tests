package scolomfr.web.tests.model.vocabulary.skos.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Selector;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.springframework.stereotype.Component;

import scolomfr.web.tests.controller.response.Result;
import scolomfr.web.tests.model.vocabulary.Vocabulary;
import scolomfr.web.tests.model.vocabulary.algorithm.AbstractAlgorithm;
import scolomfr.web.tests.model.vocabulary.algorithm.AlgorithmNotImplementedException;
import scolomfr.web.tests.model.vocabulary.algorithm.MissingPrefLabelDetector;

@Component
public class SkosMissingPrefLabelDetectorImpl extends AbstractAlgorithm implements MissingPrefLabelDetector {

	@Override
	public Result<Map<String, List<String>>> analyse(Vocabulary vocabulary) throws AlgorithmNotImplementedException {
		Map<String, List<String>> missingPrefLabels = new TreeMap<>();
		Property prefLabel = vocabulary.getModel().getProperty("http://www.w3.org/2004/02/skos/core#", "prefLabel");
		Property altLabel = vocabulary.getModel().getProperty("http://www.w3.org/2004/02/skos/core#", "altLabel");
		Selector globalSelector = new SimpleSelector(null, null, (RDFNode) null);

		StmtIterator stmts1 = vocabulary.getModel().listStatements(globalSelector);

		while (stmts1.hasNext()) {

			Statement statement = stmts1.next();
			Selector prefLabelSelector = new SimpleSelector(statement.getSubject(), prefLabel, (RDFNode) null);
			StmtIterator stmts2 = vocabulary.getModel().listStatements(prefLabelSelector);
			if (!stmts2.hasNext()) {
				missingPrefLabels.put(statement.getSubject().getURI(), new ArrayList<>());
				Selector altLabelSelector = new SimpleSelector(statement.getSubject(), altLabel, (RDFNode) null);
				StmtIterator stmts3 = vocabulary.getModel().listStatements(altLabelSelector);
				while (stmts3.hasNext()) {
					Statement altLabelStatement = stmts3.next();
					missingPrefLabels.get(statement.getSubject().getURI())
							.add(((Literal) altLabelStatement.getObject()).getString());

				}
			}
		}
		Result<Map<String, List<String>>> result = new Result<>();
		result.setContent(missingPrefLabels);
		result.setErrors(missingPrefLabels.size());
		return result;
	}

}
