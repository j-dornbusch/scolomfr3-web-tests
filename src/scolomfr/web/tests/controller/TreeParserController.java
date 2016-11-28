package scolomfr.web.tests.controller;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import scolomfr.web.tests.model.utils.Tree;
import scolomfr.web.tests.model.vocabulary.Formats;
import scolomfr.web.tests.model.vocabulary.Versions;
import scolomfr.web.tests.model.vocabulary.Vocabulary;
import scolomfr.web.tests.model.vocabulary.VocabularyFactory;
import scolomfr.web.tests.resources.MissingResourceException;

@Controller
public class TreeParserController {

	@Autowired
	private VocabularyFactory vocabularyFactory;

	@RequestMapping("/trees")
	public ModelAndView completeSkosParsing(@RequestParam(name = "uri", required = false) String uri,
			@RequestParam(name = "use_member", required = false, defaultValue = "true") Boolean useMember)
			throws MissingResourceException {
		Map<String, String> treeRoots = useMember ? getCurrentVocabulary().getVocabRoots()
				: getCurrentVocabulary().getTreeRoots();
		if (StringUtils.isEmpty(uri)) {
			Set<String> uris = treeRoots.keySet();
			uri = (String) uris.toArray()[0];
		}
		Tree<Pair<String, String>> tree = getCurrentVocabulary().getTreeForUri(uri, useMember);
		ModelAndView modelAndView = new ModelAndView("scolomfr3-trees");
		modelAndView.addObject("treeRoots", treeRoots);
		modelAndView.addObject("uri", uri);
		modelAndView.addObject("useMember", useMember);
		modelAndView.addObject("tree", tree);
		modelAndView.addObject("page", "trees");

		return modelAndView;
	}

	private Vocabulary getCurrentVocabulary() throws MissingResourceException {
		return vocabularyFactory.get(Formats.getCurrent(), Versions.getCurrent());
	}
}
