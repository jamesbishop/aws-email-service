package uk.co.blackcell.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Incoming email request data representation, contain all data necessary for the request.
 *
 * @author James Bishop
 */
@Data
public class EmailRequestDTO {

    @NotEmpty
    private String reference;

    /**
     * Sender should be null (by default), as we can't provide any sender address because
     * they would need to be pre-configured on AWS. This is simply an override facility if
     * we wish to provided another sender (which is already configured on AWS).
     */
    private String sender;

    @NotNull
    private List<String> recipients;

    @NotEmpty
    private String subject;

    @NotNull
    private TemplateDTO template;
}
