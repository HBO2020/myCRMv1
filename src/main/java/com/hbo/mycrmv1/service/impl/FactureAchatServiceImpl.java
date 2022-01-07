package com.hbo.mycrmv1.service.impl;

import com.hbo.mycrmv1.domain.FactureAchat;
import com.hbo.mycrmv1.repository.FactureAchatRepository;
import com.hbo.mycrmv1.service.FactureAchatService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FactureAchat}.
 */
@Service
@Transactional
public class FactureAchatServiceImpl implements FactureAchatService {

    private final Logger log = LoggerFactory.getLogger(FactureAchatServiceImpl.class);

    private final FactureAchatRepository factureAchatRepository;

    public FactureAchatServiceImpl(FactureAchatRepository factureAchatRepository) {
        this.factureAchatRepository = factureAchatRepository;
    }

    @Override
    public FactureAchat save(FactureAchat factureAchat) {
        log.debug("Request to save FactureAchat : {}", factureAchat);
        return factureAchatRepository.save(factureAchat);
    }

    @Override
    public Optional<FactureAchat> partialUpdate(FactureAchat factureAchat) {
        log.debug("Request to partially update FactureAchat : {}", factureAchat);

        return factureAchatRepository
            .findById(factureAchat.getId())
            .map(existingFactureAchat -> {
                if (factureAchat.getAchatIdentFac() != null) {
                    existingFactureAchat.setAchatIdentFac(factureAchat.getAchatIdentFac());
                }
                if (factureAchat.getAchatDateEffet() != null) {
                    existingFactureAchat.setAchatDateEffet(factureAchat.getAchatDateEffet());
                }
                if (factureAchat.getAchatDateUpdate() != null) {
                    existingFactureAchat.setAchatDateUpdate(factureAchat.getAchatDateUpdate());
                }
                if (factureAchat.getAchatStatusFact() != null) {
                    existingFactureAchat.setAchatStatusFact(factureAchat.getAchatStatusFact());
                }
                if (factureAchat.getAchatMontantHT() != null) {
                    existingFactureAchat.setAchatMontantHT(factureAchat.getAchatMontantHT());
                }
                if (factureAchat.getAchatMontantTVA() != null) {
                    existingFactureAchat.setAchatMontantTVA(factureAchat.getAchatMontantTVA());
                }
                if (factureAchat.getAchatMontantTTC() != null) {
                    existingFactureAchat.setAchatMontantTTC(factureAchat.getAchatMontantTTC());
                }

                return existingFactureAchat;
            })
            .map(factureAchatRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FactureAchat> findAll() {
        log.debug("Request to get all FactureAchats");
        return factureAchatRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FactureAchat> findOne(Long id) {
        log.debug("Request to get FactureAchat : {}", id);
        return factureAchatRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FactureAchat : {}", id);
        factureAchatRepository.deleteById(id);
    }
}
