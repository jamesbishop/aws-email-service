package uk.co.blackcell.dao;

import uk.co.blackcell.entity.EmailAuditTrail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for the email audit trail service
 *
 * @author  James Bishop
 */
//@Repository
public interface EmailAuditTrailRepository extends JpaRepository<EmailAuditTrail, Long> {

}