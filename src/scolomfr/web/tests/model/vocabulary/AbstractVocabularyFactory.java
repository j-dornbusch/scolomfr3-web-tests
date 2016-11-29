package scolomfr.web.tests.model.vocabulary;

import scolomfr.web.tests.resources.MissingResourceException;

@FunctionalInterface
public interface AbstractVocabularyFactory {

	Vocabulary get(Formats format, Versions version) throws MissingResourceException;

}
