package scolomfr.web.tests.model.vocabulary.xml;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.jena.rdf.model.Resource;
import org.springframework.stereotype.Component;

import scolomfr.web.tests.model.utils.Tree;
import scolomfr.web.tests.model.vocabulary.AbstractVocabulary;

@Component
public class XmlVocabulary extends AbstractVocabulary {

	@Override
	public Map<String, String> getTreeRoots() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pair<String, String>> getRevertedNarrowerRelations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pair<String, String>> getRevertedBroaderRelations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getVocabRoots() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tree<Pair<String, String>> getTreeForUri(String uri, boolean userMember) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getLabelsForStringPattern(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getInformationForUri(String uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String prefLabelWithUri(Resource resource) {
		// TODO Auto-generated method stub
		return null;
	}

}
