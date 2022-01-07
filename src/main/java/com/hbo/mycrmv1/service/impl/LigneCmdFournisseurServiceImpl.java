package com.hbo.mycrmv1.service.impl;

import com.hbo.mycrmv1.domain.LigneCmdFournisseur;
import com.hbo.mycrmv1.repository.LigneCmdFournisseurRepository;
import com.hbo.mycrmv1.service.LigneCmdFournisseurService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LigneCmdFournisseur}.
 */
@Service
@Transactional
public class LigneCmdFournisseurServiceImpl implements LigneCmdFournisseurService {

    private final Logger log = LoggerFactory.getLogger(LigneCmdFournisseurServiceImpl.class);

    private final LigneCmdFournisseurRepository ligneCmdFournisseurRepository;

    public LigneCmdFournisseurServiceImpl(LigneCmdFournisseurRepository ligneCmdFournisseurRepository) {
        this.ligneCmdFournisseurRepository = ligneCmdFournisseurRepository;
    }

    @Override
    public LigneCmdFournisseur save(LigneCmdFournisseur ligneCmdFournisseur) {
        log.debug("Request to save LigneCmdFournisseur : {}", ligneCmdFournisseur);
        return ligneCmdFournisseurRepository.save(ligneCmdFournisseur);
    }

    @Override
    public Optional<LigneCmdFournisseur> partialUpdate(LigneCmdFournisseur ligneCmdFournisseur) {
        log.debug("Request to partially update LigneCmdFournisseur : {}", ligneCmdFournisseur);

        return ligneCmdFournisseurRepository
            .findById(ligneCmdFournisseur.getId())
            .map(existingLigneCmdFournisseur -> {
                if (ligneCmdFournisseur.getCmdQnFr() != null) {
                    existingLigneCmdFournisseur.setCmdQnFr(ligneCmdFournisseur.getCmdQnFr());
                }
                if (ligneCmdFournisseur.getCmdNmPieces() != null) {
                    existingLigneCmdFournisseur.setCmdNmPieces(ligneCmdFournisseur.getCmdNmPieces());
                }

                return existingLigneCmdFournisseur;
            })
            .map(ligneCmdFournisseurRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LigneCmdFournisseur> findAll() {
        log.debug("Request to get all LigneCmdFournisseurs");
        return ligneCmdFournisseurRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LigneCmdFournisseur> findOne(Long id) {
        log.debug("Request to get LigneCmdFournisseur : {}", id);
        return ligneCmdFournisseurRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LigneCmdFournisseur : {}", id);
        ligneCmdFournisseurRepository.deleteById(id);
    }
}
