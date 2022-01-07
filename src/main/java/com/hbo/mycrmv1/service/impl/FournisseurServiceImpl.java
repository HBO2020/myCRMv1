package com.hbo.mycrmv1.service.impl;

import com.hbo.mycrmv1.domain.Fournisseur;
import com.hbo.mycrmv1.repository.FournisseurRepository;
import com.hbo.mycrmv1.service.FournisseurService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Fournisseur}.
 */
@Service
@Transactional
public class FournisseurServiceImpl implements FournisseurService {

    private final Logger log = LoggerFactory.getLogger(FournisseurServiceImpl.class);

    private final FournisseurRepository fournisseurRepository;

    public FournisseurServiceImpl(FournisseurRepository fournisseurRepository) {
        this.fournisseurRepository = fournisseurRepository;
    }

    @Override
    public Fournisseur save(Fournisseur fournisseur) {
        log.debug("Request to save Fournisseur : {}", fournisseur);
        return fournisseurRepository.save(fournisseur);
    }

    @Override
    public Optional<Fournisseur> partialUpdate(Fournisseur fournisseur) {
        log.debug("Request to partially update Fournisseur : {}", fournisseur);

        return fournisseurRepository
            .findById(fournisseur.getId())
            .map(existingFournisseur -> {
                if (fournisseur.getFrIdent() != null) {
                    existingFournisseur.setFrIdent(fournisseur.getFrIdent());
                }
                if (fournisseur.getFrRaisonSocial() != null) {
                    existingFournisseur.setFrRaisonSocial(fournisseur.getFrRaisonSocial());
                }
                if (fournisseur.getFrAdresse() != null) {
                    existingFournisseur.setFrAdresse(fournisseur.getFrAdresse());
                }
                if (fournisseur.getFrCodePostal() != null) {
                    existingFournisseur.setFrCodePostal(fournisseur.getFrCodePostal());
                }
                if (fournisseur.getFrVille() != null) {
                    existingFournisseur.setFrVille(fournisseur.getFrVille());
                }
                if (fournisseur.getFrCountry() != null) {
                    existingFournisseur.setFrCountry(fournisseur.getFrCountry());
                }
                if (fournisseur.getFrEmail() != null) {
                    existingFournisseur.setFrEmail(fournisseur.getFrEmail());
                }
                if (fournisseur.getFrNumeroMobile() != null) {
                    existingFournisseur.setFrNumeroMobile(fournisseur.getFrNumeroMobile());
                }
                if (fournisseur.getFrNumeroFax() != null) {
                    existingFournisseur.setFrNumeroFax(fournisseur.getFrNumeroFax());
                }
                if (fournisseur.getFrNumeroFixe() != null) {
                    existingFournisseur.setFrNumeroFixe(fournisseur.getFrNumeroFixe());
                }
                if (fournisseur.getFrDateCreation() != null) {
                    existingFournisseur.setFrDateCreation(fournisseur.getFrDateCreation());
                }
                if (fournisseur.getFrDateUpdate() != null) {
                    existingFournisseur.setFrDateUpdate(fournisseur.getFrDateUpdate());
                }
                if (fournisseur.getFrStatus() != null) {
                    existingFournisseur.setFrStatus(fournisseur.getFrStatus());
                }
                if (fournisseur.getFrNumeroSiret() != null) {
                    existingFournisseur.setFrNumeroSiret(fournisseur.getFrNumeroSiret());
                }

                return existingFournisseur;
            })
            .map(fournisseurRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Fournisseur> findAll(Pageable pageable) {
        log.debug("Request to get all Fournisseurs");
        return fournisseurRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Fournisseur> findOne(Long id) {
        log.debug("Request to get Fournisseur : {}", id);
        return fournisseurRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fournisseur : {}", id);
        fournisseurRepository.deleteById(id);
    }
}
