package scolomfr.web.tests.model.vocabulary;

public enum Formats {
	SKOS("SKOS"), RDF("RDF"), XML("XML"), HTML("HTML"), XSD("XSD");

	private String value;
	private static Formats current;

	private Formats(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}

	public static void setCurrentFormat(Formats newCurrent) {
		current = newCurrent;

	}

	public static Formats getCurrent() {
		return current;
	}

}
