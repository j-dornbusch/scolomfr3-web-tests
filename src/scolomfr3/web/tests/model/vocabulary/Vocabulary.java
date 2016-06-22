package scolomfr3.web.tests.model.vocabulary;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;

import scolomfr3.web.tests.model.utils.Tree;
import scolomfr3.web.tests.model.utils.Triple;

public interface Vocabulary {

	void setModel(Model model);

	List<Triple<String, String, String>> getCompleteContent();

	Map<String, String> getTreeRoots();

	Map<String, String> getVocabRoots();

	List<Pair<String, String>> getRevertedNarrowerRelations();

	List<Pair<String, String>> getRevertedBroaderRelations();

	List<Pair<String, String>> getMissingNarrowerRelations();

	List<Pair<String, String>> getMissingBroaderRelations();

	Tree<Pair<String, String>> getTreeForUri(String uri, boolean userMember);

	Map<String, String> getLabelsForStringPattern(String query);
}
