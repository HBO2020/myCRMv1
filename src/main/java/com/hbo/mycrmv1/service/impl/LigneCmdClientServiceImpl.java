package com.hbo.mycrmv1.service.impl;

import com.hbo.mycrmv1.domain.LigneCmdClient;
import com.hbo.mycrmv1.repository.LigneCmdClientRepository;
import com.hbo.mycrmv1.service.LigneCmdClientService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LigneCmdClient}.
 */
@Service
@Transactional
public class LigneCmdClientServiceImpl implements LigneCmdClientService {

    private final Logger log = LoggerFactory.getLogger(LigneCmdClientServiceImpl.class);

    private final LigneCmdClientRepository ligneCmdClientRepository;

    public LigneCmdClientServiceImpl(LigneCmdClientRepository ligneCmdClientRepository) {
        this.ligneCmdClientRepository = ligneCmdClientRepository;
    }

    @Override
    public LigneCmdClient save(LigneCmdClient ligneCmdClient) {
        log.debug("Request to save LigneCmdClient : {}", ligneCmdClient);
        return ligneCmdClientRepository.save(ligneCmdClient);
    }

    @Override
    public Optional<LigneCmdClient> partialUpdate(LigneCmdClient ligneCmdClient) {
        log.debug("Request to partially update LigneCmdClient : {}", ligneCmdClient);

        return ligneCmdClientRepository
            .findById(ligneCmdClient.getId())
            .map(existingLigneCmdClient -> {
                if (ligneCmdClient.getCmdQnCl() != null) {
                    existingLigneCmdClient.setCmdQnCl(ligneCmdClient.getCmdQnCl());
                }
                if (ligneCmdClient.getCmdNmPiecesCl() != null) {
                    existingLigneCmdClient.setCmdNmPiecesCl(ligneCmdClient.getCmdNmPiecesCl());
                }

                return existingLigneCmdClient;
            })
            .map(ligneCmdClientRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LigneCmdClient> findAll() {
        log.debug("Request to get all LigneCmdClients");
        return ligneCmdClientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LigneCmdClient> findOne(Long id) {
        log.debug("Request to get LigneCmdClient : {}", id);
        return ligneCmdClientRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LigneCmdClient : {}", id);
        ligneCmdClientRepository.deleteById(id);
    }
}
