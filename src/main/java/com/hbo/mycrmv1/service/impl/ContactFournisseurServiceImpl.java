package com.hbo.mycrmv1.service.impl;

import com.hbo.mycrmv1.domain.ContactFournisseur;
import com.hbo.mycrmv1.repository.ContactFournisseurRepository;
import com.hbo.mycrmv1.service.ContactFournisseurService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ContactFournisseur}.
 */
@Service
@Transactional
public class ContactFournisseurServiceImpl implements ContactFournisseurService {

    private final Logger log = LoggerFactory.getLogger(ContactFournisseurServiceImpl.class);

    private final ContactFournisseurRepository contactFournisseurRepository;

    public ContactFournisseurServiceImpl(ContactFournisseurRepository contactFournisseurRepository) {
        this.contactFournisseurRepository = contactFournisseurRepository;
    }

    @Override
    public ContactFournisseur save(ContactFournisseur contactFournisseur) {
        log.debug("Request to save ContactFournisseur : {}", contactFournisseur);
        return contactFournisseurRepository.save(contactFournisseur);
    }

    @Override
    public Optional<ContactFournisseur> partialUpdate(ContactFournisseur contactFournisseur) {
        log.debug("Request to partially update ContactFournisseur : {}", contactFournisseur);

        return contactFournisseurRepository
            .findById(contactFournisseur.getId())
            .map(existingContactFournisseur -> {
                if (contactFournisseur.getContactFrName() != null) {
                    existingContactFournisseur.setContactFrName(contactFournisseur.getContactFrName());
                }
                if (contactFournisseur.getContactfrPrenom() != null) {
                    existingContactFournisseur.setContactfrPrenom(contactFournisseur.getContactfrPrenom());
                }
                if (contactFournisseur.getContactFrEmail() != null) {
                    existingContactFournisseur.setContactFrEmail(contactFournisseur.getContactFrEmail());
                }
                if (contactFournisseur.getContactFrMobilePhone() != null) {
                    existingContactFournisseur.setContactFrMobilePhone(contactFournisseur.getContactFrMobilePhone());
                }
                if (contactFournisseur.getContactFrStatus() != null) {
                    existingContactFournisseur.setContactFrStatus(contactFournisseur.getContactFrStatus());
                }

                return existingContactFournisseur;
            })
            .map(contactFournisseurRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactFournisseur> findAll() {
        log.debug("Request to get all ContactFournisseurs");
        return contactFournisseurRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContactFournisseur> findOne(Long id) {
        log.debug("Request to get ContactFournisseur : {}", id);
        return contactFournisseurRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContactFournisseur : {}", id);
        contactFournisseurRepository.deleteById(id);
    }
}
