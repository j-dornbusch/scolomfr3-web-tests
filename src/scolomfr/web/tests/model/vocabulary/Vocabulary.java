package scolomfr.web.tests.model.vocabulary;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;

import scolomfr.web.tests.controller.response.Result;
import scolomfr.web.tests.model.utils.Tree;
import scolomfr.web.tests.model.vocabulary.algorithm.Algorithm;
import scolomfr.web.tests.model.vocabulary.algorithm.AlgorithmNotImplementedException;

public interface Vocabulary {

	/**
	 * Injection du Model dans le vocabulaire (utilisé par la factory)
	 * 
	 * @param model
	 */
	void setModel(Model model);

	Model getModel();

	/**
	 * Détection des racines de l'arbre des relations narrower/broader
	 * 
	 * @return une map de paires uri, prefLabel
	 */
	Map<String, String> getTreeRoots();

	/**
	 * Détection des sommets sources de relations member Ce sont les
	 * vocabulaires
	 * 
	 * @return une map de paires uri, prefLabel
	 */
	Map<String, String> getVocabRoots();

	/**
	 * Liste les relations narrower qui ont pour pendant une relation broader
	 * 
	 * @return une liste de paires uri, prefLabel
	 */
	List<Pair<String, String>> getRevertedNarrowerRelations();

	/**
	 * Liste les relations broader qui ont pour pendant une relation narrower
	 * 
	 * @return une liste de paires uri, prefLabel
	 */
	List<Pair<String, String>> getRevertedBroaderRelations();

	Tree<Pair<String, String>> getTreeForUri(String uri, boolean useMember);

	/**
	 * @param query
	 *            une requête de recherche en language naturel
	 * @return une liste de paires uri, prefLabel
	 */
	Map<String, String> getLabelsForStringPattern(String query);

	/**
	 * Retourne tous les triplets dont la ressource désignée par URI est le
	 * sujet
	 * 
	 * @param uri
	 * @return une map predicat -> prefLabel
	 */
	Map<String, String> getInformationForUri(String uri);

	Result<?> apply(Algorithm algorithm) throws AlgorithmNotImplementedException;

	String prefLabelWithUri(Resource resource);

}
