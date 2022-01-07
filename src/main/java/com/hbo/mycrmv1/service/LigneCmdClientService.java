package com.hbo.mycrmv1.service;

import com.hbo.mycrmv1.domain.LigneCmdClient;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link LigneCmdClient}.
 */
public interface LigneCmdClientService {
    /**
     * Save a ligneCmdClient.
     *
     * @param ligneCmdClient the entity to save.
     * @return the persisted entity.
     */
    LigneCmdClient save(LigneCmdClient ligneCmdClient);

    /**
     * Partially updates a ligneCmdClient.
     *
     * @param ligneCmdClient the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LigneCmdClient> partialUpdate(LigneCmdClient ligneCmdClient);

    /**
     * Get all the ligneCmdClients.
     *
     * @return the list of entities.
     */
    List<LigneCmdClient> findAll();

    /**
     * Get the "id" ligneCmdClient.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LigneCmdClient> findOne(Long id);

    /**
     * Delete the "id" ligneCmdClient.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
