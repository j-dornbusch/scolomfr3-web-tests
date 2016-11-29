package scolomfr.web.tests.model.vocabulary.skos.algorithm;

import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import scolomfr.web.tests.model.vocabulary.Formats;
import scolomfr.web.tests.model.vocabulary.Versions;
import scolomfr.web.tests.model.vocabulary.Vocabulary;
import scolomfr.web.tests.model.vocabulary.VocabularyFactory;
import scolomfr.web.tests.model.vocabulary.skos.SkosVocabulary;
import scolomfr.web.tests.resources.MissingResourceException;
import scolomfr.web.tests.resources.ResourcesLoader;

@Component
public class SkosVocabularyFactoryImpl implements VocabularyFactory {

	@Autowired
	ResourcesLoader resourcesLoader;

	@Autowired
	private Environment env;

	@Autowired
	private SkosVocabulary skosVocabulary;

	@Override
	public Vocabulary get(Formats format, Versions version) throws MissingResourceException {
		Model model = ModelFactory.createDefaultModel();
		String inputFileNameProperty = String.format("%s.%s", format, version);
		String inputFileName = env.getProperty(inputFileNameProperty);

		if (StringUtils.isEmpty(inputFileName)) {
			throw new MissingResourceException(
					"Aucun fichier n'est fourni pour le format " + format + " et la version " + version + ".");
		}

		InputStream in = resourcesLoader.loadResource(inputFileName);
		if (null == in) {
			throw new MissingResourceException(
					"Le fichier " + inputFileName + " est absent pour le format " + format.toString());
		}
		model.read(in, null);
		skosVocabulary.setModel(model);
		return skosVocabulary;
	}

}
