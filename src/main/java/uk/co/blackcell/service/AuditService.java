package uk.co.blackcell.service;

import uk.co.blackcell.entity.EmailAuditTrail;

public interface AuditService {

    void save(EmailAuditTrail trail);

}
