package com.hbo.mycrmv1.service;

import com.hbo.mycrmv1.domain.Carts;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Carts}.
 */
public interface CartsService {
    /**
     * Save a carts.
     *
     * @param carts the entity to save.
     * @return the persisted entity.
     */
    Carts save(Carts carts);

    /**
     * Partially updates a carts.
     *
     * @param carts the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Carts> partialUpdate(Carts carts);

    /**
     * Get all the carts.
     *
     * @return the list of entities.
     */
    List<Carts> findAll();

    /**
     * Get the "id" carts.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Carts> findOne(Long id);

    /**
     * Delete the "id" carts.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
