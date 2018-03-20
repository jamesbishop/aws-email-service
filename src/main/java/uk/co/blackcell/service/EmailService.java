package uk.co.blackcell.service;

import uk.co.blackcell.gateway.type.EmailContent;

public interface EmailService {

    void sendMessage(EmailContent content);

}
