package scolomfr.web.tests.model.vocabulary.html;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import scolomfr.web.tests.model.vocabulary.Formats;

public class HTMLFormatSelected implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		return Formats.getCurrent() == Formats.HTML;
	}

}
