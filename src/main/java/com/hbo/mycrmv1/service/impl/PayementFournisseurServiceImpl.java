package com.hbo.mycrmv1.service.impl;

import com.hbo.mycrmv1.domain.PayementFournisseur;
import com.hbo.mycrmv1.repository.PayementFournisseurRepository;
import com.hbo.mycrmv1.service.PayementFournisseurService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PayementFournisseur}.
 */
@Service
@Transactional
public class PayementFournisseurServiceImpl implements PayementFournisseurService {

    private final Logger log = LoggerFactory.getLogger(PayementFournisseurServiceImpl.class);

    private final PayementFournisseurRepository payementFournisseurRepository;

    public PayementFournisseurServiceImpl(PayementFournisseurRepository payementFournisseurRepository) {
        this.payementFournisseurRepository = payementFournisseurRepository;
    }

    @Override
    public PayementFournisseur save(PayementFournisseur payementFournisseur) {
        log.debug("Request to save PayementFournisseur : {}", payementFournisseur);
        return payementFournisseurRepository.save(payementFournisseur);
    }

    @Override
    public Optional<PayementFournisseur> partialUpdate(PayementFournisseur payementFournisseur) {
        log.debug("Request to partially update PayementFournisseur : {}", payementFournisseur);

        return payementFournisseurRepository
            .findById(payementFournisseur.getId())
            .map(existingPayementFournisseur -> {
                if (payementFournisseur.getPayementFrIdent() != null) {
                    existingPayementFournisseur.setPayementFrIdent(payementFournisseur.getPayementFrIdent());
                }
                if (payementFournisseur.getPayementFrDate() != null) {
                    existingPayementFournisseur.setPayementFrDate(payementFournisseur.getPayementFrDate());
                }
                if (payementFournisseur.getPayementFrMode() != null) {
                    existingPayementFournisseur.setPayementFrMode(payementFournisseur.getPayementFrMode());
                }
                if (payementFournisseur.getPayementFrEcheance() != null) {
                    existingPayementFournisseur.setPayementFrEcheance(payementFournisseur.getPayementFrEcheance());
                }
                if (payementFournisseur.getPayementFrMontant() != null) {
                    existingPayementFournisseur.setPayementFrMontant(payementFournisseur.getPayementFrMontant());
                }

                return existingPayementFournisseur;
            })
            .map(payementFournisseurRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PayementFournisseur> findAll() {
        log.debug("Request to get all PayementFournisseurs");
        return payementFournisseurRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PayementFournisseur> findOne(Long id) {
        log.debug("Request to get PayementFournisseur : {}", id);
        return payementFournisseurRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PayementFournisseur : {}", id);
        payementFournisseurRepository.deleteById(id);
    }
}
