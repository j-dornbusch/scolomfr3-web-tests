package scolomfr.web.tests.model.vocabulary.algorithm;

import scolomfr.web.tests.model.vocabulary.Vocabulary;

public interface Algorithm<T> {

	T analyse(Vocabulary vocabulary);

}