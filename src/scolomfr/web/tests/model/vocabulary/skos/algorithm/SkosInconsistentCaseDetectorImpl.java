package scolomfr.web.tests.model.vocabulary.skos.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import scolomfr.web.tests.controller.response.Result;
import scolomfr.web.tests.model.utils.Tree;
import scolomfr.web.tests.model.utils.Tree.Node;
import scolomfr.web.tests.model.vocabulary.Vocabulary;
import scolomfr.web.tests.model.vocabulary.algorithm.InconsistentCaseDetector;

@Component
@PropertySource("classpath:properties/label.properties")
public class SkosInconsistentCaseDetectorImpl implements InconsistentCaseDetector {

	@Autowired
	Environment env;

	private int nbListUppercase;
	private int nbListLowercase;

	private String[] nomsPropres;

	/**
	 * 
	 * @return une map uri du parent -> liste des preflabels problématiques
	 */
	@Override
	public Result<Map<String, List<String>>> analyse(Vocabulary vocabulary) {

		Map<String, String> vocabRoots = vocabulary.getVocabRoots();
		Iterator<String> it = vocabRoots.keySet().iterator();
		Map<String, List<String>> inconsistent = new TreeMap<>();
		nbListUppercase = 0;
		nbListLowercase = 0;
		while (it.hasNext()) {
			String uri = it.next();
			Tree<Pair<String, String>> tree = vocabulary.getTreeForUri(uri, true);
			detectCaseConcernsRecursively(tree.getRoot().getData(), tree.getRoot().getChildren(), inconsistent,
					vocabulary);
		}
		Result<Map<String, List<String>>> result = new Result<>();
		result.setContent(inconsistent);
		result.setErrors(inconsistent.size());
		return result;

	}

	private void detectCaseConcernsRecursively(Pair<String, String> rootData, List<Node<Pair<String, String>>> children,
			Map<String, List<String>> inconsistent, Vocabulary vocabulary) {
		Boolean caseConcern = false;

		Iterator<Node<Pair<String, String>>> it = children.iterator();
		List<String> labels = new ArrayList<>();
		while (it.hasNext()) {
			Tree.Node<Pair<String, String>> node = it.next();
			String label = node.getData().getRight();
			labels.add(label);
			Character firstLetter = label.charAt(0);
			if (!Character.isLetter(firstLetter)) {
				continue;
			}
			boolean firstLetterIsLower = Character.isLowerCase(firstLetter);
			Boolean isSigle = isSigle(label);
			boolean isNompropre = isNomPropre(label);

			if (!firstLetterIsLower && !isNompropre && !isSigle) {
				caseConcern = true;
			}

			Tree<Pair<String, String>> tree = vocabulary.getTreeForUri(node.getData().getLeft(), false);
			detectCaseConcernsRecursively(tree.getRoot().getData(), tree.getRoot().getChildren(), inconsistent,
					vocabulary);
		}
		if (caseConcern) {
			ArrayList<String> labelsWithFlags = new ArrayList<>();
			Iterator<String> it2 = labels.iterator();
			while (it2.hasNext()) {
				String label = it2.next();
				if (!Character.isLowerCase(label.charAt(0)) && !isNomPropre(label) && !isSigle(label)) {
					label = "-" + label;
				} else {
					label = "+" + label;
				}
				labelsWithFlags.add(label);
			}

			inconsistent.put(rootData.getLeft(), labelsWithFlags);
		}
	}

	private Boolean isSigle(String label) {
		Character firstLetter = label.charAt(0);
		boolean firstLetterIsLower = Character.isLowerCase(firstLetter);
		Character secondLetter = label.length() > 1 ? label.charAt(1) : 'X';
		boolean secondLetterIsLower = label.length() > 1 && Character.isLowerCase(secondLetter);
		return !firstLetterIsLower && !secondLetterIsLower;
	}

	private boolean isNomPropre(String label) {

		for (String nomPropre : getNomsPropres()) {
			if (label.toLowerCase().startsWith(nomPropre.toLowerCase())) {
				return true;
			}

		}
		return false;
	}

	private String[] getNomsPropres() {
		if (null == nomsPropres) {
			String property = env.getProperty("proper");
			nomsPropres = property.split(",");
		}
		return nomsPropres;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see scolomfr.web.tests.model.vocabulary.skos.InconsistentCaseDetector#
	 * getNbListUppercase()
	 */
	@Override
	public int getNbListUppercase() {
		return nbListUppercase;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see scolomfr.web.tests.model.vocabulary.skos.InconsistentCaseDetector#
	 * getNbListLowercase()
	 */
	@Override
	public int getNbListLowercase() {
		return nbListLowercase;
	}

}
