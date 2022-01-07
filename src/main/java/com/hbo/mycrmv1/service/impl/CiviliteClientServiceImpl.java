package com.hbo.mycrmv1.service.impl;

import com.hbo.mycrmv1.domain.CiviliteClient;
import com.hbo.mycrmv1.repository.CiviliteClientRepository;
import com.hbo.mycrmv1.service.CiviliteClientService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CiviliteClient}.
 */
@Service
@Transactional
public class CiviliteClientServiceImpl implements CiviliteClientService {

    private final Logger log = LoggerFactory.getLogger(CiviliteClientServiceImpl.class);

    private final CiviliteClientRepository civiliteClientRepository;

    public CiviliteClientServiceImpl(CiviliteClientRepository civiliteClientRepository) {
        this.civiliteClientRepository = civiliteClientRepository;
    }

    @Override
    public CiviliteClient save(CiviliteClient civiliteClient) {
        log.debug("Request to save CiviliteClient : {}", civiliteClient);
        return civiliteClientRepository.save(civiliteClient);
    }

    @Override
    public Optional<CiviliteClient> partialUpdate(CiviliteClient civiliteClient) {
        log.debug("Request to partially update CiviliteClient : {}", civiliteClient);

        return civiliteClientRepository
            .findById(civiliteClient.getId())
            .map(existingCiviliteClient -> {
                if (civiliteClient.getCiviliteLibelleCl() != null) {
                    existingCiviliteClient.setCiviliteLibelleCl(civiliteClient.getCiviliteLibelleCl());
                }
                if (civiliteClient.getCiviliteCodeCl() != null) {
                    existingCiviliteClient.setCiviliteCodeCl(civiliteClient.getCiviliteCodeCl());
                }

                return existingCiviliteClient;
            })
            .map(civiliteClientRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CiviliteClient> findAll() {
        log.debug("Request to get all CiviliteClients");
        return civiliteClientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CiviliteClient> findOne(Long id) {
        log.debug("Request to get CiviliteClient : {}", id);
        return civiliteClientRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CiviliteClient : {}", id);
        civiliteClientRepository.deleteById(id);
    }
}
