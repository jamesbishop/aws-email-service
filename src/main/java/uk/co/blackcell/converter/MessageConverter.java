package uk.co.blackcell.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.blackcell.dto.EmailRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.io.IOException;
import java.util.function.Function;

/**
 * Message converter to convert a generic message into our desired request data.
 *
 * @author James Bishop
 */
@Component
@Slf4j
public class MessageConverter implements Function<TextMessage, EmailRequestDTO> {

    @Override
    public EmailRequestDTO apply(final TextMessage message) {
        try {
            return new ObjectMapper().readValue(message.getText(), EmailRequestDTO.class);

        } catch(JMSException | IOException ex) {
            log.error("An error occurred while converting message: ", ex);
            throw new IllegalArgumentException(ex);
        }
    }
}
