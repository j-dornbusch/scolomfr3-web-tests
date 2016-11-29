package scolomfr.web.tests.model.vocabulary.algorithm;

import java.util.List;
import java.util.Map;

import scolomfr.web.tests.controller.response.Result;
import scolomfr.web.tests.model.vocabulary.Vocabulary;

public interface InconsistentCaseDetector extends Algorithm {

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
	@Override
	public Result<Map<String, List<String>>> analyse(Vocabulary vocabulary) throws AlgorithmNotImplementedException;

}