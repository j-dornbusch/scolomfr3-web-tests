package scolomfr.web.tests.model.vocabulary.xml;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import scolomfr.web.tests.model.vocabulary.Formats;

public class XmlFormatSelected implements Condition {

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		System.out.println("________________test XML");
		return Formats.getCurrent().equals(Formats.XML);
	}

}
