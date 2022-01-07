package com.hbo.mycrmv1.service;

import com.hbo.mycrmv1.domain.LigneLivFournisseur;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link LigneLivFournisseur}.
 */
public interface LigneLivFournisseurService {
    /**
     * Save a ligneLivFournisseur.
     *
     * @param ligneLivFournisseur the entity to save.
     * @return the persisted entity.
     */
    LigneLivFournisseur save(LigneLivFournisseur ligneLivFournisseur);

    /**
     * Partially updates a ligneLivFournisseur.
     *
     * @param ligneLivFournisseur the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LigneLivFournisseur> partialUpdate(LigneLivFournisseur ligneLivFournisseur);

    /**
     * Get all the ligneLivFournisseurs.
     *
     * @return the list of entities.
     */
    List<LigneLivFournisseur> findAll();

    /**
     * Get the "id" ligneLivFournisseur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LigneLivFournisseur> findOne(Long id);

    /**
     * Delete the "id" ligneLivFournisseur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
