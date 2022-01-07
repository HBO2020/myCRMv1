package com.hbo.mycrmv1.service;

import com.hbo.mycrmv1.domain.LivraisonCl;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link LivraisonCl}.
 */
public interface LivraisonClService {
    /**
     * Save a livraisonCl.
     *
     * @param livraisonCl the entity to save.
     * @return the persisted entity.
     */
    LivraisonCl save(LivraisonCl livraisonCl);

    /**
     * Partially updates a livraisonCl.
     *
     * @param livraisonCl the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LivraisonCl> partialUpdate(LivraisonCl livraisonCl);

    /**
     * Get all the livraisonCls.
     *
     * @return the list of entities.
     */
    List<LivraisonCl> findAll();

    /**
     * Get the "id" livraisonCl.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LivraisonCl> findOne(Long id);

    /**
     * Delete the "id" livraisonCl.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
