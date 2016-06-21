package scolomfr3.web.tests.controller;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import scolomfr3.web.tests.model.utils.Tree;
import scolomfr3.web.tests.model.vocabulary.Vocabulary;

@Controller
public class TreeParserController {

	@Autowired
	private Vocabulary skosVocabulary;

	@RequestMapping("/trees")
	public ModelAndView completeSkosParsing(@RequestParam(name = "uri", required = false) String uri) {

		Map<String, String> treeRoots = skosVocabulary.getTreeRoots();
		if (StringUtils.isEmpty(uri)) {
			Set<String> uris = treeRoots.keySet();
			uri = (String) uris.toArray()[0];
		}
		Tree<Pair<String, String>> tree = skosVocabulary.getTreeForUri(uri);
		ModelAndView modelAndView = new ModelAndView("scolomfr3-trees");
		modelAndView.addObject("treeRoots", treeRoots);
		modelAndView.addObject("uri", uri);
		modelAndView.addObject("tree", tree);
		return modelAndView;
	}
}
