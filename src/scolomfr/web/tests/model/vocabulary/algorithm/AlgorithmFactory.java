package scolomfr.web.tests.model.vocabulary.algorithm;

public interface AlgorithmFactory {

	Algorithm getAlgorithm(Class interfaceToImplement) throws AlgorithmNotImplementedException;

}