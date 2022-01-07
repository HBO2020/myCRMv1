package com.hbo.mycrmv1.service.impl;

import com.hbo.mycrmv1.domain.CommandeFournisseur;
import com.hbo.mycrmv1.repository.CommandeFournisseurRepository;
import com.hbo.mycrmv1.service.CommandeFournisseurService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CommandeFournisseur}.
 */
@Service
@Transactional
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

    private final Logger log = LoggerFactory.getLogger(CommandeFournisseurServiceImpl.class);

    private final CommandeFournisseurRepository commandeFournisseurRepository;

    public CommandeFournisseurServiceImpl(CommandeFournisseurRepository commandeFournisseurRepository) {
        this.commandeFournisseurRepository = commandeFournisseurRepository;
    }

    @Override
    public CommandeFournisseur save(CommandeFournisseur commandeFournisseur) {
        log.debug("Request to save CommandeFournisseur : {}", commandeFournisseur);
        return commandeFournisseurRepository.save(commandeFournisseur);
    }

    @Override
    public Optional<CommandeFournisseur> partialUpdate(CommandeFournisseur commandeFournisseur) {
        log.debug("Request to partially update CommandeFournisseur : {}", commandeFournisseur);

        return commandeFournisseurRepository
            .findById(commandeFournisseur.getId())
            .map(existingCommandeFournisseur -> {
                if (commandeFournisseur.getCmdIdenFr() != null) {
                    existingCommandeFournisseur.setCmdIdenFr(commandeFournisseur.getCmdIdenFr());
                }
                if (commandeFournisseur.getCmdDateEffetFr() != null) {
                    existingCommandeFournisseur.setCmdDateEffetFr(commandeFournisseur.getCmdDateEffetFr());
                }
                if (commandeFournisseur.getCmdDateLivraisonFr() != null) {
                    existingCommandeFournisseur.setCmdDateLivraisonFr(commandeFournisseur.getCmdDateLivraisonFr());
                }

                return existingCommandeFournisseur;
            })
            .map(commandeFournisseurRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommandeFournisseur> findAll() {
        log.debug("Request to get all CommandeFournisseurs");
        return commandeFournisseurRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CommandeFournisseur> findOne(Long id) {
        log.debug("Request to get CommandeFournisseur : {}", id);
        return commandeFournisseurRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CommandeFournisseur : {}", id);
        commandeFournisseurRepository.deleteById(id);
    }
}
