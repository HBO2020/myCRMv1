package com.hbo.mycrmv1.service;

import com.hbo.mycrmv1.domain.CommandeFournisseur;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CommandeFournisseur}.
 */
public interface CommandeFournisseurService {
    /**
     * Save a commandeFournisseur.
     *
     * @param commandeFournisseur the entity to save.
     * @return the persisted entity.
     */
    CommandeFournisseur save(CommandeFournisseur commandeFournisseur);

    /**
     * Partially updates a commandeFournisseur.
     *
     * @param commandeFournisseur the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CommandeFournisseur> partialUpdate(CommandeFournisseur commandeFournisseur);

    /**
     * Get all the commandeFournisseurs.
     *
     * @return the list of entities.
     */
    List<CommandeFournisseur> findAll();

    /**
     * Get the "id" commandeFournisseur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CommandeFournisseur> findOne(Long id);

    /**
     * Delete the "id" commandeFournisseur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
