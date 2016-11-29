package scolomfr.web.tests.model.vocabulary;

import java.util.EnumMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import scolomfr.web.tests.model.vocabulary.skos.algorithm.SkosVocabularyFactoryImpl;
import scolomfr.web.tests.model.vocabulary.xml.XmlVocabularyFactoryImpl;
import scolomfr.web.tests.resources.MissingResourceException;
import scolomfr.web.tests.resources.ResourcesLoader;

@Component
@Scope("application")
@PropertySource("classpath:properties/file-location.properties")
public class AbstractVocabularyFactoryImpl implements AbstractVocabularyFactory {

	@Autowired
	ResourcesLoader resourcesLoader;

	@Autowired
	private Environment env;

	@Autowired
	XmlVocabularyFactoryImpl xmlVocabularyfactory;

	@Autowired
	SkosVocabularyFactoryImpl skosVocabularyfactory;

	private EnumMap<Formats, EnumMap<Versions, Vocabulary>> vocabularyPool = new EnumMap<>(Formats.class);;

	@Override
	public Vocabulary get(Formats format, Versions version) throws MissingResourceException {
		if (!vocabularyPool.containsKey(format)) {
			vocabularyPool.put(format, new EnumMap<Versions, Vocabulary>(Versions.class));
		}
		if (vocabularyPool.get(format).containsKey(version)) {
			return vocabularyPool.get(format).get(version);
		}
		VocabularyFactory factory;
		switch (format) {
		case SKOS:
			factory = skosVocabularyfactory;
			break;
		case XML:
			factory = xmlVocabularyfactory;
			break;
		default:
			throw new MissingResourceException("Le format " + format + " n'est pas implémenté.");
		}
		Vocabulary vocabulary = factory.get(format, version);
		vocabularyPool.get(format).put(version, vocabulary);
		return vocabulary;
	}

}
