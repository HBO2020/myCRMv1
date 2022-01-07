package com.hbo.mycrmv1.service.impl;

import com.hbo.mycrmv1.domain.LigneLivFournisseur;
import com.hbo.mycrmv1.repository.LigneLivFournisseurRepository;
import com.hbo.mycrmv1.service.LigneLivFournisseurService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LigneLivFournisseur}.
 */
@Service
@Transactional
public class LigneLivFournisseurServiceImpl implements LigneLivFournisseurService {

    private final Logger log = LoggerFactory.getLogger(LigneLivFournisseurServiceImpl.class);

    private final LigneLivFournisseurRepository ligneLivFournisseurRepository;

    public LigneLivFournisseurServiceImpl(LigneLivFournisseurRepository ligneLivFournisseurRepository) {
        this.ligneLivFournisseurRepository = ligneLivFournisseurRepository;
    }

    @Override
    public LigneLivFournisseur save(LigneLivFournisseur ligneLivFournisseur) {
        log.debug("Request to save LigneLivFournisseur : {}", ligneLivFournisseur);
        return ligneLivFournisseurRepository.save(ligneLivFournisseur);
    }

    @Override
    public Optional<LigneLivFournisseur> partialUpdate(LigneLivFournisseur ligneLivFournisseur) {
        log.debug("Request to partially update LigneLivFournisseur : {}", ligneLivFournisseur);

        return ligneLivFournisseurRepository
            .findById(ligneLivFournisseur.getId())
            .map(existingLigneLivFournisseur -> {
                if (ligneLivFournisseur.getLivFrQuantite() != null) {
                    existingLigneLivFournisseur.setLivFrQuantite(ligneLivFournisseur.getLivFrQuantite());
                }
                if (ligneLivFournisseur.getLivFrNmPieces() != null) {
                    existingLigneLivFournisseur.setLivFrNmPieces(ligneLivFournisseur.getLivFrNmPieces());
                }
                if (ligneLivFournisseur.getLivFrTotalPrix() != null) {
                    existingLigneLivFournisseur.setLivFrTotalPrix(ligneLivFournisseur.getLivFrTotalPrix());
                }

                return existingLigneLivFournisseur;
            })
            .map(ligneLivFournisseurRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LigneLivFournisseur> findAll() {
        log.debug("Request to get all LigneLivFournisseurs");
        return ligneLivFournisseurRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LigneLivFournisseur> findOne(Long id) {
        log.debug("Request to get LigneLivFournisseur : {}", id);
        return ligneLivFournisseurRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LigneLivFournisseur : {}", id);
        ligneLivFournisseurRepository.deleteById(id);
    }
}
