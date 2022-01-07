package com.hbo.mycrmv1.service.impl;

import com.hbo.mycrmv1.domain.CommandeClient;
import com.hbo.mycrmv1.repository.CommandeClientRepository;
import com.hbo.mycrmv1.service.CommandeClientService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CommandeClient}.
 */
@Service
@Transactional
public class CommandeClientServiceImpl implements CommandeClientService {

    private final Logger log = LoggerFactory.getLogger(CommandeClientServiceImpl.class);

    private final CommandeClientRepository commandeClientRepository;

    public CommandeClientServiceImpl(CommandeClientRepository commandeClientRepository) {
        this.commandeClientRepository = commandeClientRepository;
    }

    @Override
    public CommandeClient save(CommandeClient commandeClient) {
        log.debug("Request to save CommandeClient : {}", commandeClient);
        return commandeClientRepository.save(commandeClient);
    }

    @Override
    public Optional<CommandeClient> partialUpdate(CommandeClient commandeClient) {
        log.debug("Request to partially update CommandeClient : {}", commandeClient);

        return commandeClientRepository
            .findById(commandeClient.getId())
            .map(existingCommandeClient -> {
                if (commandeClient.getCmdIdenCl() != null) {
                    existingCommandeClient.setCmdIdenCl(commandeClient.getCmdIdenCl());
                }
                if (commandeClient.getCmdDateEffetCl() != null) {
                    existingCommandeClient.setCmdDateEffetCl(commandeClient.getCmdDateEffetCl());
                }
                if (commandeClient.getCmdDateLivraisonCl() != null) {
                    existingCommandeClient.setCmdDateLivraisonCl(commandeClient.getCmdDateLivraisonCl());
                }

                return existingCommandeClient;
            })
            .map(commandeClientRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommandeClient> findAll() {
        log.debug("Request to get all CommandeClients");
        return commandeClientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CommandeClient> findOne(Long id) {
        log.debug("Request to get CommandeClient : {}", id);
        return commandeClientRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommandeClient : {}", id);
        commandeClientRepository.deleteById(id);
    }
}
