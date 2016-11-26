package scolomfr.web.tests.model.vocabulary;

import java.io.InputStream;
import java.util.EnumMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import scolomfr.web.tests.model.vocabulary.rdf.RdfVocabulary;
import scolomfr.web.tests.model.vocabulary.skos.SkosVocabulary;
import scolomfr.web.tests.resources.MissingRessourceException;
import scolomfr.web.tests.resources.ResourcesLoader;

@Component
@Scope("application")
@PropertySource("classpath:properties/file-location.properties")
public class VocabularyFactoryImpl implements VocabularyFactory {

	@Autowired
	ResourcesLoader resourcesLoader;

	@Autowired
	private Environment env;

	private EnumMap<Formats, EnumMap<Versions, Vocabulary>> vocabularyPool;

	@Override
	public Vocabulary get(Formats format, Versions version) throws MissingRessourceException {
		if (null == vocabularyPool) {
			vocabularyPool = new EnumMap<>(Formats.class);
		}
		if (!vocabularyPool.containsKey(format)) {
			vocabularyPool.put(format, new EnumMap<Versions, Vocabulary>(Versions.class));
		}
		if (vocabularyPool.get(format).containsKey(version)) {
			return vocabularyPool.get(format).get(version);
		}
		Vocabulary vocabulary;
		Model model = ModelFactory.createDefaultModel();
		String inputFileName;
		String inputFileNameProperty = String.format("%s.%s", format, version);
		inputFileName = env.getProperty(inputFileNameProperty);

		if (StringUtils.isEmpty(inputFileName)) {
			throw new MissingRessourceException("No file is provided for format " + format + " and version " + version);
		}
		switch (format) {
		case RDF:
			vocabulary = new RdfVocabulary();
			break;
		case SKOS:
			vocabulary = new SkosVocabulary();
			break;
		default:
			throw new MissingRessourceException("Format " + format + " is not implemnted ");
		}
		InputStream in = resourcesLoader.loadResource(inputFileName);
		if (null == in) {
			throw new MissingRessourceException(
					"File " + inputFileName + " is missing for vocabulary identifier" + format.toString());
		}
		model.read(in, null);
		vocabulary.setModel(model);
		vocabularyPool.get(format).put(version, vocabulary);
		return vocabulary;
	}

}
