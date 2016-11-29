package scolomfr.web.tests.model.vocabulary.algorithm;

@FunctionalInterface
public interface AlgorithmFactory {

	Algorithm getAlgorithm(Class<?> interfaceToImplement) throws AlgorithmNotImplementedException;

}