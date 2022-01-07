package com.hbo.mycrmv1.service;

import com.hbo.mycrmv1.domain.LivraisonFr;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link LivraisonFr}.
 */
public interface LivraisonFrService {
    /**
     * Save a livraisonFr.
     *
     * @param livraisonFr the entity to save.
     * @return the persisted entity.
     */
    LivraisonFr save(LivraisonFr livraisonFr);

    /**
     * Partially updates a livraisonFr.
     *
     * @param livraisonFr the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LivraisonFr> partialUpdate(LivraisonFr livraisonFr);

    /**
     * Get all the livraisonFrs.
     *
     * @return the list of entities.
     */
    List<LivraisonFr> findAll();

    /**
     * Get the "id" livraisonFr.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LivraisonFr> findOne(Long id);

    /**
     * Delete the "id" livraisonFr.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
