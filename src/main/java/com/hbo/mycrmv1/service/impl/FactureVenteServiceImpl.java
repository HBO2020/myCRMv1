package com.hbo.mycrmv1.service.impl;

import com.hbo.mycrmv1.domain.FactureVente;
import com.hbo.mycrmv1.repository.FactureVenteRepository;
import com.hbo.mycrmv1.service.FactureVenteService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FactureVente}.
 */
@Service
@Transactional
public class FactureVenteServiceImpl implements FactureVenteService {

    private final Logger log = LoggerFactory.getLogger(FactureVenteServiceImpl.class);

    private final FactureVenteRepository factureVenteRepository;

    public FactureVenteServiceImpl(FactureVenteRepository factureVenteRepository) {
        this.factureVenteRepository = factureVenteRepository;
    }

    @Override
    public FactureVente save(FactureVente factureVente) {
        log.debug("Request to save FactureVente : {}", factureVente);
        return factureVenteRepository.save(factureVente);
    }

    @Override
    public Optional<FactureVente> partialUpdate(FactureVente factureVente) {
        log.debug("Request to partially update FactureVente : {}", factureVente);

        return factureVenteRepository
            .findById(factureVente.getId())
            .map(existingFactureVente -> {
                if (factureVente.getVenteIdentFac() != null) {
                    existingFactureVente.setVenteIdentFac(factureVente.getVenteIdentFac());
                }
                if (factureVente.getVenteDateEffet() != null) {
                    existingFactureVente.setVenteDateEffet(factureVente.getVenteDateEffet());
                }
                if (factureVente.getVenteDateUpdate() != null) {
                    existingFactureVente.setVenteDateUpdate(factureVente.getVenteDateUpdate());
                }
                if (factureVente.getVenteStatusFact() != null) {
                    existingFactureVente.setVenteStatusFact(factureVente.getVenteStatusFact());
                }
                if (factureVente.getVenteMontantHT() != null) {
                    existingFactureVente.setVenteMontantHT(factureVente.getVenteMontantHT());
                }
                if (factureVente.getVenteMontantTVA() != null) {
                    existingFactureVente.setVenteMontantTVA(factureVente.getVenteMontantTVA());
                }
                if (factureVente.getVenteMontantTTC() != null) {
                    existingFactureVente.setVenteMontantTTC(factureVente.getVenteMontantTTC());
                }

                return existingFactureVente;
            })
            .map(factureVenteRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FactureVente> findAll() {
        log.debug("Request to get all FactureVentes");
        return factureVenteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FactureVente> findOne(Long id) {
        log.debug("Request to get FactureVente : {}", id);
        return factureVenteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FactureVente : {}", id);
        factureVenteRepository.deleteById(id);
    }
}
