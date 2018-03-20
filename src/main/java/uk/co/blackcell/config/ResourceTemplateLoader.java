package uk.co.blackcell.config;

import freemarker.cache.TemplateLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * FreeMarker loads resource template "files" through this implementation of TemplateLoader
 *
 * @author James Bishop
 */
@Slf4j
@Component(value = "resourceTemplates")
public class ResourceTemplateLoader implements TemplateLoader {

    @Value(value = "${freemarker.template.directory}")
    private String templateDirectory;

    @Override
    public Object findTemplateSource(final String templateName) throws IOException {
        log.debug("findTemplateSource('{}') method called.", templateName);

        return getClass().getClassLoader().getResourceAsStream(templateDirectory + templateName);
    }

    @Override
    public long getLastModified(final Object templateSource) {
        log.debug("getLastModified('{}') method called.", templateSource.getClass().getName());

        return 0;
    }

    @Override
    public Reader getReader(final Object templateSource, final String encoding) throws IOException {
        log.debug("getReader('{}','{}') method called.", templateSource.getClass().getName(), encoding);

        return new BufferedReader(new InputStreamReader((InputStream) templateSource, encoding));
    }

    @Override
    public void closeTemplateSource(final Object templateSource) throws IOException {
        log.debug("closeTemplateSource('{}') method called.", templateSource.getClass().getName());

        ((InputStream) templateSource).close();
    }
}
