package uk.co.blackcell.converter;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import uk.co.blackcell.dto.EmailRequestDTO;
import uk.co.blackcell.gateway.type.EmailContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.function.Function;

@Component
@Slf4j
public class EmailConverter implements Function<EmailRequestDTO, EmailContent> {

    @Autowired
    private Configuration configuration;

    @Value("${email.default.sender.address}")
    private String emailDefaultSenderAddress;

    @Override
    public EmailContent apply(final EmailRequestDTO request) {
        log.debug("apply('{}') method called. ", request);

        EmailContent parameters = new EmailContent();

        parameters.setSender(request.getSender());

        if(request.getSender() == null || request.getSender().isEmpty()) {
            parameters.setSender(emailDefaultSenderAddress);
        }

        parameters.setRecipients(request.getRecipients());
        parameters.setSubject(request.getSubject());

        Object templateData = request.getTemplate().getParameters();
        String templateName = String.format("%s_en_GB.ftlh", request.getTemplate().getType());

        parameters.setBody(parse(templateData, templateName));

        return parameters;
    }

    /**
     * This method will map all the key/value pairs associated to the template.
     */
    public String parse(final Object toGetParsed, final String templateName) {
        log.info("Freemarker configuration object is: {}" + configuration);

        try (Writer out = new StringWriter()) {
            log.debug("Loading template: ('{}')", templateName);

            Template template = configuration.getTemplate(templateName);
            template.process(toGetParsed, out);

            return out.toString();

        } catch (IOException | TemplateException e) {
            log.error("The parsing generated an error for this template: " + templateName, e);
            throw new IllegalStateException(e);
        }
    }

}
