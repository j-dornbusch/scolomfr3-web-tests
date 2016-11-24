package scolomfr.web.tests.model.vocabulary.skos;

import java.util.List;
import java.util.Map;

import scolomfr.web.tests.model.vocabulary.Vocabulary;
import scolomfr.web.tests.model.vocabulary.algorithm.Algorithm;

public interface InconsistentCaseDetector extends Algorithm<Map<String, List<String>>> {

	/**
	 * 
	 * @return le nombre de listes en minuscules
	 */
	int getNbListUppercase();

	/**
	 * 
	 * @return le nombre de listes en majuscules ou indeterminées
	 */
	int getNbListLowercase();

	/**
	 * 
	 * @return une map uri du parent -> liste des preflabels problématiques
	 */
	public Map<String, List<String>> analyse(Vocabulary vocabulary);

}