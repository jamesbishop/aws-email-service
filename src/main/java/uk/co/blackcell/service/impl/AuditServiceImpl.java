package uk.co.blackcell.service.impl;

import uk.co.blackcell.dao.EmailAuditTrailRepository;
import uk.co.blackcell.entity.EmailAuditTrail;
import uk.co.blackcell.service.AuditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuditServiceImpl implements AuditService {

    @Autowired
    private EmailAuditTrailRepository repository;

    @Override
    public void save(final EmailAuditTrail record) {
        log.debug("save('{}') method called.", record);

        repository.saveAndFlush(record);
    }
}
