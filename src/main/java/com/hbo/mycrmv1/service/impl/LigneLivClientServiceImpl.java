package com.hbo.mycrmv1.service.impl;

import com.hbo.mycrmv1.domain.LigneLivClient;
import com.hbo.mycrmv1.repository.LigneLivClientRepository;
import com.hbo.mycrmv1.service.LigneLivClientService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LigneLivClient}.
 */
@Service
@Transactional
public class LigneLivClientServiceImpl implements LigneLivClientService {

    private final Logger log = LoggerFactory.getLogger(LigneLivClientServiceImpl.class);

    private final LigneLivClientRepository ligneLivClientRepository;

    public LigneLivClientServiceImpl(LigneLivClientRepository ligneLivClientRepository) {
        this.ligneLivClientRepository = ligneLivClientRepository;
    }

    @Override
    public LigneLivClient save(LigneLivClient ligneLivClient) {
        log.debug("Request to save LigneLivClient : {}", ligneLivClient);
        return ligneLivClientRepository.save(ligneLivClient);
    }

    @Override
    public Optional<LigneLivClient> partialUpdate(LigneLivClient ligneLivClient) {
        log.debug("Request to partially update LigneLivClient : {}", ligneLivClient);

        return ligneLivClientRepository
            .findById(ligneLivClient.getId())
            .map(existingLigneLivClient -> {
                if (ligneLivClient.getLivQuantiteCl() != null) {
                    existingLigneLivClient.setLivQuantiteCl(ligneLivClient.getLivQuantiteCl());
                }
                if (ligneLivClient.getLivNmPiecesCl() != null) {
                    existingLigneLivClient.setLivNmPiecesCl(ligneLivClient.getLivNmPiecesCl());
                }
                if (ligneLivClient.getLivTotalPrixCl() != null) {
                    existingLigneLivClient.setLivTotalPrixCl(ligneLivClient.getLivTotalPrixCl());
                }

                return existingLigneLivClient;
            })
            .map(ligneLivClientRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LigneLivClient> findAll() {
        log.debug("Request to get all LigneLivClients");
        return ligneLivClientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LigneLivClient> findOne(Long id) {
        log.debug("Request to get LigneLivClient : {}", id);
        return ligneLivClientRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LigneLivClient : {}", id);
        ligneLivClientRepository.deleteById(id);
    }
}
