package scolomfr3.web.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import scolomfr3.web.tests.model.vocabulary.Vocabularies;
import scolomfr3.web.tests.model.vocabulary.Vocabulary;
import scolomfr3.web.tests.model.vocabulary.VocabularyFactory;

@Configuration
public class AppConfig {

	@Bean
	@Scope("application")
	@Autowired
	public Vocabulary skosVocabulary(VocabularyFactory vocabularyFactory) {
		return vocabularyFactory.get(Vocabularies.SCOLOMFR3_SKOS);
	}

	@Bean
	@Scope("application")
	@Autowired
	public Vocabulary rdfVocabulary(VocabularyFactory vocabularyFactory) {
		return vocabularyFactory.get(Vocabularies.SCOLOMFR3_RDF);
	}
}