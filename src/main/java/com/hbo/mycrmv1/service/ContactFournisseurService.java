package com.hbo.mycrmv1.service;

import com.hbo.mycrmv1.domain.ContactFournisseur;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ContactFournisseur}.
 */
public interface ContactFournisseurService {
    /**
     * Save a contactFournisseur.
     *
     * @param contactFournisseur the entity to save.
     * @return the persisted entity.
     */
    ContactFournisseur save(ContactFournisseur contactFournisseur);

    /**
     * Partially updates a contactFournisseur.
     *
     * @param contactFournisseur the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContactFournisseur> partialUpdate(ContactFournisseur contactFournisseur);

    /**
     * Get all the contactFournisseurs.
     *
     * @return the list of entities.
     */
    List<ContactFournisseur> findAll();

    /**
     * Get the "id" contactFournisseur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContactFournisseur> findOne(Long id);

    /**
     * Delete the "id" contactFournisseur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
