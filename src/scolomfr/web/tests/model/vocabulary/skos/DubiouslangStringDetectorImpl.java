package scolomfr.web.tests.model.vocabulary.skos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Selector;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.springframework.stereotype.Component;

import com.atlascopco.hunspell.Hunspell;

import scolomfr.web.tests.model.vocabulary.Vocabulary;
import scolomfr.web.tests.model.vocabulary.algorithm.AbstractAlgorithm;

@Component
public class DubiouslangStringDetectorImpl extends AbstractAlgorithm<Map<String, List<String>>>
		implements DubiousLangStringDetector {

	@Override
	public Map<String, List<String>> analyse(Vocabulary vocabulary) {
		Pattern abbreviationsPattern = Pattern.compile("^([A-Za-zàèéêîôûù]+\\.\\s*)+([A-Za-zàèéêîôûù]+\\.*\\s*)$");

		Map<String, List<String>> dubious = new HashMap<>();
		Hunspell speller = new Hunspell("/usr/share/hunspell/fr_FR.dic", "/usr/share/hunspell/fr_FR.aff");
		Property prefLabel = vocabulary.getModel().getProperty("http://www.w3.org/2004/02/skos/core#", "prefLabel");
		Property altLabel = vocabulary.getModel().getProperty("http://www.w3.org/2004/02/skos/core#", "altLabel");
		Property scopeNote = vocabulary.getModel().getProperty("http://www.w3.org/2004/02/skos/core#", "scopeNote");
		Selector prefLabelSelector = new SimpleSelector(null, prefLabel, (RDFNode) null);
		Selector altLabelSelector = new SimpleSelector(null, altLabel, (RDFNode) null);
		Selector scopeNoteSelector = new SimpleSelector(null, scopeNote, (RDFNode) null);
		StmtIterator stmts1 = vocabulary.getModel().listStatements(prefLabelSelector);
		StmtIterator stmts2 = vocabulary.getModel().listStatements(altLabelSelector);
		StmtIterator stmts3 = vocabulary.getModel().listStatements(scopeNoteSelector);
		ExtendedIterator<Statement> stmts = stmts1.andThen(stmts2).andThen(stmts3);
		Resource vocab001 = vocabulary.getModel().getResource("http://data.education.fr/voc/scolomfr/scolomfr-voc-001");
		Resource vocab006 = vocabulary.getModel().getResource("http://data.education.fr/voc/scolomfr/scolomfr-voc-006");
		Resource vocab024 = vocabulary.getModel().getResource("http://data.education.fr/voc/scolomfr/scolomfr-voc-024");

		while (stmts.hasNext()) {
			Statement statement = (Statement) stmts.next();

			if (memberOfVocab(vocab001, statement.getSubject(), vocabulary)
					|| memberOfVocab(vocab024, statement.getSubject(), vocabulary)
					|| memberOfVocab(vocab006, statement.getSubject(), vocabulary)) {
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

	private boolean memberOfVocab(Resource vocab, Resource subject, Vocabulary vocabulary) {
		Property member = vocabulary.getModel().getProperty("http://www.w3.org/2004/02/skos/core#", "member");
		Selector memberSelector = new SimpleSelector(vocab, member, subject);
		StmtIterator stmtIterator = vocabulary.getModel().listStatements(memberSelector);
		return stmtIterator.hasNext();
	}

}
