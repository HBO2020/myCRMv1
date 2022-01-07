package com.hbo.mycrmv1.service;

import com.hbo.mycrmv1.domain.PayementFournisseur;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PayementFournisseur}.
 */
public interface PayementFournisseurService {
    /**
     * Save a payementFournisseur.
     *
     * @param payementFournisseur the entity to save.
     * @return the persisted entity.
     */
    PayementFournisseur save(PayementFournisseur payementFournisseur);

    /**
     * Partially updates a payementFournisseur.
     *
     * @param payementFournisseur the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PayementFournisseur> partialUpdate(PayementFournisseur payementFournisseur);

    /**
     * Get all the payementFournisseurs.
     *
     * @return the list of entities.
     */
    List<PayementFournisseur> findAll();

    /**
     * Get the "id" payementFournisseur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PayementFournisseur> findOne(Long id);

    /**
     * Delete the "id" payementFournisseur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
