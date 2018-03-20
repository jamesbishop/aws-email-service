package uk.co.blackcell.entity;

import uk.co.blackcell.dao.converter.TemplateTypeConverter;
import uk.co.blackcell.entity.type.TemplateType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * This entity class will provide access to the email audit data.
 *
 * @author  James Bishop
 */
@Entity
@Data
public  class EmailAuditTrail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    /**
     * A client specific reference (not unique) to identity this request.
     */
    @Column(nullable = false)
    private String reference;

    /**
     * The specific template which needs to be sent (an enumerated list)
     */
    @Column(nullable = false)
    @Convert(converter = TemplateTypeConverter.class)
    private TemplateType templateType;

    /**
     * The entire incoming request data.
     */
    @Column(nullable = false)
    private String incomingRequest;

    /**
     * The fully parsed email content sent out to the recipient.
     */
    @Column(nullable = false)
    private String emailContent;

    /**
     * The recipient address for this email request.
     */
    @Column(nullable = false)
    private String recipientAddress;

    /**
     * The date that the email was sent.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date sentDateTime;

}
