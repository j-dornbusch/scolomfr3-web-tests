package scolomfr3.web.tests.resources;

import java.io.InputStream;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("application")
public class ResourcesLoader {
	public InputStream loadResource(final String path) {
		return ResourcesLoader.class.getResourceAsStream(path);

	}
}
