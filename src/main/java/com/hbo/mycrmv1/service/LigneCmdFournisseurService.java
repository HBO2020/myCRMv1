package com.hbo.mycrmv1.service;

import com.hbo.mycrmv1.domain.LigneCmdFournisseur;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link LigneCmdFournisseur}.
 */
public interface LigneCmdFournisseurService {
    /**
     * Save a ligneCmdFournisseur.
     *
     * @param ligneCmdFournisseur the entity to save.
     * @return the persisted entity.
     */
    LigneCmdFournisseur save(LigneCmdFournisseur ligneCmdFournisseur);

    /**
     * Partially updates a ligneCmdFournisseur.
     *
     * @param ligneCmdFournisseur the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LigneCmdFournisseur> partialUpdate(LigneCmdFournisseur ligneCmdFournisseur);

    /**
     * Get all the ligneCmdFournisseurs.
     *
     * @return the list of entities.
     */
    List<LigneCmdFournisseur> findAll();

    /**
     * Get the "id" ligneCmdFournisseur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LigneCmdFournisseur> findOne(Long id);

    /**
     * Delete the "id" ligneCmdFournisseur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
