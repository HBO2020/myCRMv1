package com.hbo.mycrmv1.service;

import com.hbo.mycrmv1.domain.PayementClient;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link PayementClient}.
 */
public interface PayementClientService {
    /**
     * Save a payementClient.
     *
     * @param payementClient the entity to save.
     * @return the persisted entity.
     */
    PayementClient save(PayementClient payementClient);

    /**
     * Partially updates a payementClient.
     *
     * @param payementClient the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PayementClient> partialUpdate(PayementClient payementClient);

    /**
     * Get all the payementClients.
     *
     * @return the list of entities.
     */
    List<PayementClient> findAll();

    /**
     * Get the "id" payementClient.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PayementClient> findOne(Long id);

    /**
     * Delete the "id" payementClient.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
