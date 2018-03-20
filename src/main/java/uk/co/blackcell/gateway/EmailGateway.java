package uk.co.blackcell.gateway;

import uk.co.blackcell.gateway.type.EmailContent;

/**
 * Email Gateway Service definition, providing the standard interface for implementing gateways.
 *
 * @author James Bishop
 *
 */
public interface EmailGateway {

    /**
     * Send an HTML based email to a particular customer with the request parameters provided
     *
     * @param parameters The Email parameters
     * @return true if the email is sent, false otherwise
     */
    boolean sendHTMLEmail(EmailContent parameters);

}

