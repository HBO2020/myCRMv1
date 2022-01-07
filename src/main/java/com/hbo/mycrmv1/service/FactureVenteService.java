package com.hbo.mycrmv1.service;

import com.hbo.mycrmv1.domain.FactureVente;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link FactureVente}.
 */
public interface FactureVenteService {
    /**
     * Save a factureVente.
     *
     * @param factureVente the entity to save.
     * @return the persisted entity.
     */
    FactureVente save(FactureVente factureVente);

    /**
     * Partially updates a factureVente.
     *
     * @param factureVente the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FactureVente> partialUpdate(FactureVente factureVente);

    /**
     * Get all the factureVentes.
     *
     * @return the list of entities.
     */
    List<FactureVente> findAll();

    /**
     * Get the "id" factureVente.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FactureVente> findOne(Long id);

    /**
     * Delete the "id" factureVente.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
