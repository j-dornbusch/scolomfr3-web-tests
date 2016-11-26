package scolomfr.web.tests.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import scolomfr.web.tests.model.vocabulary.Vocabulary;

@Controller
public class SearchControler {

	@Autowired
	private Vocabulary vocabulary;

	@RequestMapping("/search")
	public String displaySearchPage(@RequestParam(name = "query", required = false, defaultValue = "") String query,
			@RequestParam(name = "uri", required = false, defaultValue = "") String uri, Model model) {
		// La recherche est faite soit par uri, soit par query en language
		// naturel
		Map<String, Map<String, String>> results = new HashMap<>();
		model.addAttribute("searchBy", "query");
		Set<String> uris = new HashSet<>();
		// Si une uri est fournie, elle est prioritaire
		if (!StringUtils.isEmpty(uri)) {
			model.addAttribute("uri", uri);
			model.addAttribute("searchBy", "uri");
			uris.add(uri);
		} else if (!StringUtils.isEmpty(query)) {
			// Sinon, par défaut, on cherche par query
			uris = vocabulary.getLabelsForStringPattern(query).keySet();
			model.addAttribute("query", query);
		}
		Iterator<String> it = uris.iterator();
		while (it.hasNext()) {
			String entryUri = (String) it.next();
			results.put(entryUri, vocabulary.getInformationForUri(entryUri));
		}
		model.addAttribute("results", results);
		model.addAttribute("page", "search");
		return "scolomfr3-search";
	}

	@RequestMapping(value = "/search/autocomplete", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody Map<String, String> autocompleteOnLabels(
			@RequestParam(name = "query", required = false, defaultValue = "") String query) {
		return vocabulary.getLabelsForStringPattern(query);
	}
}
