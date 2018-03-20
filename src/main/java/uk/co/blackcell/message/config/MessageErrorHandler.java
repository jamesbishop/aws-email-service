package uk.co.blackcell.message.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

@Service
@Slf4j
public class MessageErrorHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable ex) {
        log.error("An error occurred in the message listener, error was: ", ex);
    }

}
