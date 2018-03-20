package uk.co.blackcell.service.impl;

import uk.co.blackcell.gateway.EmailGateway;
import uk.co.blackcell.gateway.type.EmailContent;
import uk.co.blackcell.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailGateway gateway;

    @Override
    public void sendMessage(final EmailContent content) {
        log.debug("sendMessage('{}') method called. ", content);

        if(!gateway.sendHTMLEmail(content)) {
            log.error("An error occurred while trying to send the email!");
        }
    }
}
