package uk.co.blackcell.message;

import uk.co.blackcell.converter.EmailConverter;
import uk.co.blackcell.converter.MessageConverter;
import uk.co.blackcell.dto.EmailRequestDTO;
import uk.co.blackcell.entity.EmailAuditTrail;
import uk.co.blackcell.entity.type.TemplateType;
import uk.co.blackcell.gateway.type.EmailContent;
import uk.co.blackcell.service.AuditService;
import uk.co.blackcell.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.Date;

/**
 * This class provide all the available listeners for consuming messages.
 *
 * @author James Bishop
 */
@Service
@Slf4j
public class EmailQueueMessageListener {

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuditService auditService;

    @Autowired
    private MessageConverter messageConverter;

    @Autowired
    private EmailConverter emailConverter;

    @JmsListener(id = "processMessage", destination = "${email.service.destination.queue.name}")
    public void processMessage(final Message message) throws JMSException {
        log.info("processMessage('{}') method called.", message);

        if (message instanceof TextMessage) {
            handleMessage((TextMessage)message);
        }

        message.acknowledge();
    }

    private void handleMessage(final TextMessage message) throws JMSException {
        log.info("handleMessage('{} bytes') method called.", message.getText().length());

        final EmailRequestDTO request = messageConverter.apply(message);
        final EmailContent content = emailConverter.apply(request);

        emailService.sendMessage(content);

        EmailAuditTrail trail = new EmailAuditTrail();
        trail.setReference(request.getReference());
        trail.setTemplateType(TemplateType.valueOf(request.getTemplate().getType()));
        trail.setIncomingRequest(message.getText());
        trail.setEmailContent(content.getBody());
        trail.setRecipientAddress(request.getRecipients().get(0));
        trail.setSentDateTime(new Date());

        auditService.save(trail);
    }
}
