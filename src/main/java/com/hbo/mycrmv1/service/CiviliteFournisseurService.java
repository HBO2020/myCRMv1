package com.hbo.mycrmv1.service;

import com.hbo.mycrmv1.domain.CiviliteFournisseur;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CiviliteFournisseur}.
 */
public interface CiviliteFournisseurService {
    /**
     * Save a civiliteFournisseur.
     *
     * @param civiliteFournisseur the entity to save.
     * @return the persisted entity.
     */
    CiviliteFournisseur save(CiviliteFournisseur civiliteFournisseur);

    /**
     * Partially updates a civiliteFournisseur.
     *
     * @param civiliteFournisseur the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CiviliteFournisseur> partialUpdate(CiviliteFournisseur civiliteFournisseur);

    /**
     * Get all the civiliteFournisseurs.
     *
     * @return the list of entities.
     */
    List<CiviliteFournisseur> findAll();

    /**
     * Get the "id" civiliteFournisseur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CiviliteFournisseur> findOne(Long id);

    /**
     * Delete the "id" civiliteFournisseur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
