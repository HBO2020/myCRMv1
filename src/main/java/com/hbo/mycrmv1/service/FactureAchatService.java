package com.hbo.mycrmv1.service;

import com.hbo.mycrmv1.domain.FactureAchat;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link FactureAchat}.
 */
public interface FactureAchatService {
    /**
     * Save a factureAchat.
     *
     * @param factureAchat the entity to save.
     * @return the persisted entity.
     */
    FactureAchat save(FactureAchat factureAchat);

    /**
     * Partially updates a factureAchat.
     *
     * @param factureAchat the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FactureAchat> partialUpdate(FactureAchat factureAchat);

    /**
     * Get all the factureAchats.
     *
     * @return the list of entities.
     */
    List<FactureAchat> findAll();

    /**
     * Get the "id" factureAchat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FactureAchat> findOne(Long id);

    /**
     * Delete the "id" factureAchat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
