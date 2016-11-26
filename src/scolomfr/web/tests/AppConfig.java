package scolomfr.web.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.RequestScope;

import scolomfr.web.tests.model.vocabulary.Formats;
import scolomfr.web.tests.model.vocabulary.Versions;
import scolomfr.web.tests.model.vocabulary.Vocabulary;
import scolomfr.web.tests.model.vocabulary.VocabularyFactory;
import scolomfr.web.tests.resources.MissingRessourceException;

@Configuration
public class AppConfig {

	@Bean
	@RequestScope
	@Autowired
	public Vocabulary vocabulary(VocabularyFactory vocabularyFactory) throws MissingRessourceException {
		return vocabularyFactory.get(Formats.getCurrent(), Versions.getCurrent());
	}

	@Bean
	@Scope("application")
	@Autowired
	public Vocabulary rdfVocabulary(VocabularyFactory vocabularyFactory) throws MissingRessourceException {
		return vocabularyFactory.get(Formats.RDF, Versions.getCurrent());
	}
}