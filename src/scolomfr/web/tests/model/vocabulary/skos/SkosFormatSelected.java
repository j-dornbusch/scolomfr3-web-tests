package scolomfr.web.tests.model.vocabulary.skos;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import scolomfr.web.tests.model.vocabulary.Formats;

public class SkosFormatSelected implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		System.out.println("________________test SKOS");
		return Formats.getCurrent().equals(Formats.SKOS);
	}

}
