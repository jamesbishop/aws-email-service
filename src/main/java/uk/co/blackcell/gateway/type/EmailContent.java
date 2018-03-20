package uk.co.blackcell.gateway.type;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO encapsulating the necessary parameters required for email sending
 *
 * @author James Bishop
 */
@NoArgsConstructor
@Data
public class EmailContent {

    private String sender;

    private List<String> recipients = new ArrayList<>();

    private String subject;

    private String body;
}
