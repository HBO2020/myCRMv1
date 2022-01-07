package com.hbo.mycrmv1.service;

import com.hbo.mycrmv1.domain.LigneLivClient;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link LigneLivClient}.
 */
public interface LigneLivClientService {
    /**
     * Save a ligneLivClient.
     *
     * @param ligneLivClient the entity to save.
     * @return the persisted entity.
     */
    LigneLivClient save(LigneLivClient ligneLivClient);

    /**
     * Partially updates a ligneLivClient.
     *
     * @param ligneLivClient the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LigneLivClient> partialUpdate(LigneLivClient ligneLivClient);

    /**
     * Get all the ligneLivClients.
     *
     * @return the list of entities.
     */
    List<LigneLivClient> findAll();

    /**
     * Get the "id" ligneLivClient.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LigneLivClient> findOne(Long id);

    /**
     * Delete the "id" ligneLivClient.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
