package com.hbo.mycrmv1.service.impl;

import com.hbo.mycrmv1.domain.CiviliteFournisseur;
import com.hbo.mycrmv1.repository.CiviliteFournisseurRepository;
import com.hbo.mycrmv1.service.CiviliteFournisseurService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CiviliteFournisseur}.
 */
@Service
@Transactional
public class CiviliteFournisseurServiceImpl implements CiviliteFournisseurService {

    private final Logger log = LoggerFactory.getLogger(CiviliteFournisseurServiceImpl.class);

    private final CiviliteFournisseurRepository civiliteFournisseurRepository;

    public CiviliteFournisseurServiceImpl(CiviliteFournisseurRepository civiliteFournisseurRepository) {
        this.civiliteFournisseurRepository = civiliteFournisseurRepository;
    }

    @Override
    public CiviliteFournisseur save(CiviliteFournisseur civiliteFournisseur) {
        log.debug("Request to save CiviliteFournisseur : {}", civiliteFournisseur);
        return civiliteFournisseurRepository.save(civiliteFournisseur);
    }

    @Override
    public Optional<CiviliteFournisseur> partialUpdate(CiviliteFournisseur civiliteFournisseur) {
        log.debug("Request to partially update CiviliteFournisseur : {}", civiliteFournisseur);

        return civiliteFournisseurRepository
            .findById(civiliteFournisseur.getId())
            .map(existingCiviliteFournisseur -> {
                if (civiliteFournisseur.getCiviliteFrLibelle() != null) {
                    existingCiviliteFournisseur.setCiviliteFrLibelle(civiliteFournisseur.getCiviliteFrLibelle());
                }
                if (civiliteFournisseur.getCiviliteFrCode() != null) {
                    existingCiviliteFournisseur.setCiviliteFrCode(civiliteFournisseur.getCiviliteFrCode());
                }

                return existingCiviliteFournisseur;
            })
            .map(civiliteFournisseurRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CiviliteFournisseur> findAll() {
        log.debug("Request to get all CiviliteFournisseurs");
        return civiliteFournisseurRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CiviliteFournisseur> findOne(Long id) {
        log.debug("Request to get CiviliteFournisseur : {}", id);
        return civiliteFournisseurRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CiviliteFournisseur : {}", id);
        civiliteFournisseurRepository.deleteById(id);
    }
}
