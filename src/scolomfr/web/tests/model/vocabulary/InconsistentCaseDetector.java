package scolomfr.web.tests.model.vocabulary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import scolomfr.web.tests.model.utils.Tree;
import scolomfr.web.tests.model.utils.Tree.Node;

public class InconsistentCaseDetector extends AbstractAlgorithm<Map<String, List<String>>> {

	private int nbListUppercase;
	private int nbListLowercase;

	private List<String> nomsPropres = new ArrayList<String>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			add("Celtes");
			add("Clovis");
			add("Rome");
			add("État");
			add("Napoléon");
			add("Guyane");
			add("Flandres");
			add("Éduthèque");
			add("François");
			add("Françaises");
			add("Homo");
			add("Louis");
			add("henri");
			add("Byzance");
			add("amaya");
			add("windows");
			add("netscape");
			add("opera");
			add("mozilla");
			add("safari");
			add("google");
			add("microsoft");
			add("ms-windows");
			add("linux");
			add("android");
			add("blackberry");
			add("ios");
			add("mac");
			add("symbian");
			add("Chrome");
		}
	};

	/**
	 * 
	 * @return une map uri du parent -> liste des preflabels problématiques
	 */
	@Override
	public Map<String, List<String>> analyse(Vocabulary vocabulary) {

		Map<String, String> vocabRoots = vocabulary.getVocabRoots();
		Iterator<String> it = vocabRoots.keySet().iterator();
		Map<String, List<String>> results = new HashMap<>();
		nbListUppercase = 0;
		nbListLowercase = 0;
		while (it.hasNext()) {
			String uri = it.next();
			Tree<Pair<String, String>> tree = vocabulary.getTreeForUri(uri, true);
			detectCaseConcernsRecursively(tree.getRoot().getData(), tree.getRoot().getChildren(), results, vocabulary);
		}
		return results;

	}

	private void detectCaseConcernsRecursively(Pair<String, String> rootData, List<Node<Pair<String, String>>> children,
			Map<String, List<String>> results, Vocabulary vocabulary) {
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
			detectCaseConcernsRecursively(tree.getRoot().getData(), tree.getRoot().getChildren(), results, vocabulary);
		}
		if (caseConcern) {
			List<String> labelsWithFlags = new ArrayList<>();
			Iterator<String> it2 = labels.iterator();
			while (it2.hasNext()) {
				String label = (String) it2.next();
				if (!Character.isLowerCase(label.charAt(0)) && !isNomPropre(label) && !isSigle(label)) {
					label = "-" + label;
				} else {
					label = "+" + label;
				}
				labelsWithFlags.add(label);
			}

			results.put(rootData.getLeft(), labelsWithFlags);
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
		for (String nomPropre : nomsPropres) {
			if (label.toLowerCase().startsWith(nomPropre.toLowerCase())) {
				return true;
			}

		}
		return false;
	}

	/**
	 * 
	 * @return le nombre de listes en minuscules
	 */
	public int getNbListUppercase() {
		return nbListUppercase;
	}

	/**
	 * 
	 * @return le nombre de listes en majuscules ou indeterminées
	 */
	public int getNbListLowercase() {
		return nbListLowercase;
	}

}
