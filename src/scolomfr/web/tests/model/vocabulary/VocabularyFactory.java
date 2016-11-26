package scolomfr.web.tests.model.vocabulary;

import scolomfr.web.tests.resources.MissingRessourceException;

@FunctionalInterface
public interface VocabularyFactory {

	Vocabulary get(Formats format, Versions version) throws MissingRessourceException;

}
