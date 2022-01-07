package com.hbo.mycrmv1.service;

import com.hbo.mycrmv1.domain.CommandeClient;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CommandeClient}.
 */
public interface CommandeClientService {
    /**
     * Save a commandeClient.
     *
     * @param commandeClient the entity to save.
     * @return the persisted entity.
     */
    CommandeClient save(CommandeClient commandeClient);

    /**
     * Partially updates a commandeClient.
     *
     * @param commandeClient the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CommandeClient> partialUpdate(CommandeClient commandeClient);

    /**
     * Get all the commandeClients.
     *
     * @return the list of entities.
     */
    List<CommandeClient> findAll();

    /**
     * Get the "id" commandeClient.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CommandeClient> findOne(Long id);

    /**
     * Delete the "id" commandeClient.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
