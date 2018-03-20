package uk.co.blackcell.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Simple data transport object for provided template details.
 *
 * @author James Bishop
 */
@Data
public class TemplateDTO {

    @NotNull
    private String type;

    @NotNull
    private Map<String, String> parameters;
}
