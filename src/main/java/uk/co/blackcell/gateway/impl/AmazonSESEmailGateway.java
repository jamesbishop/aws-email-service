package uk.co.blackcell.gateway.impl;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.ConfigurationSetDoesNotExistException;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.MailFromDomainNotVerifiedException;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.MessageRejectedException;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import uk.co.blackcell.gateway.EmailGateway;
import uk.co.blackcell.gateway.type.EmailContent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Email gateway implementation that uses the AWS SES system to send emails.
 *
 * @author James Bishop
 */
@Slf4j
@Service
public class AmazonSESEmailGateway implements EmailGateway {

    @Override
    public boolean sendHTMLEmail(final EmailContent parameters) {
        log.debug("sendHTMLEmail('{}') method called.", parameters);

        return sendMessage(parameters, createMessage(parameters));
    }

    /**
     * Utility method used to invoke the Amazon SES service to send the email
     *
     * @param content The email request data parameters necessary for email sending.
     * @param message The message instance to send
     * @return true for success, false otherwise
     */
    private boolean sendMessage(final EmailContent content, final Message message) {
        log.debug("sendMessage('{}','{}') method called.", content, message);

        Destination destination = new Destination().withToAddresses(content.getRecipients());
        SendEmailRequest request = new SendEmailRequest().withSource(content.getSender()).withDestination(destination).withMessage(message);

        try {
            AmazonSimpleEmailServiceClientBuilder builder = AmazonSimpleEmailServiceClientBuilder.standard();

            builder.setRegion(Region.getRegion(Regions.EU_WEST_1).toString());
            AmazonSimpleEmailService client = builder.build();

            client.sendEmail(request);

            log.info("Email request was successfully sent to: " + content.getRecipients());

            return Boolean.TRUE;

        } catch (MessageRejectedException | MailFromDomainNotVerifiedException | ConfigurationSetDoesNotExistException ex) {
            log.error("Error occured sending email", ex);
            return Boolean.FALSE;
        }
    }

    /**
     * Utility method used to populate a Message instance ready for sending
     *
     * @param parameters The email parameters used for population of content.
     * @return The newly created message instance for sending the request.
     */
    private Message createMessage(final EmailContent parameters) {
        log.debug("createMessage('{}') method called.", parameters);

        Content subject = new Content().withData(parameters.getSubject());
        Content emailBody = new Content().withData(parameters.getBody());

        Body body = new Body().withHtml(emailBody);

        return new Message().withSubject(subject).withBody(body);
    }
}
