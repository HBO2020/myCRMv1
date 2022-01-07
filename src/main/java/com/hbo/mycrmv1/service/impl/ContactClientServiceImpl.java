package com.hbo.mycrmv1.service.impl;

import com.hbo.mycrmv1.domain.ContactClient;
import com.hbo.mycrmv1.repository.ContactClientRepository;
import com.hbo.mycrmv1.service.ContactClientService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ContactClient}.
 */
@Service
@Transactional
public class ContactClientServiceImpl implements ContactClientService {

    private final Logger log = LoggerFactory.getLogger(ContactClientServiceImpl.class);

    private final ContactClientRepository contactClientRepository;

    public ContactClientServiceImpl(ContactClientRepository contactClientRepository) {
        this.contactClientRepository = contactClientRepository;
    }

    @Override
    public ContactClient save(ContactClient contactClient) {
        log.debug("Request to save ContactClient : {}", contactClient);
        return contactClientRepository.save(contactClient);
    }

    @Override
    public Optional<ContactClient> partialUpdate(ContactClient contactClient) {
        log.debug("Request to partially update ContactClient : {}", contactClient);

        return contactClientRepository
            .findById(contactClient.getId())
            .map(existingContactClient -> {
                if (contactClient.getContactNameCl() != null) {
                    existingContactClient.setContactNameCl(contactClient.getContactNameCl());
                }
                if (contactClient.getContactPrenomCl() != null) {
                    existingContactClient.setContactPrenomCl(contactClient.getContactPrenomCl());
                }
                if (contactClient.getContactEmailCl() != null) {
                    existingContactClient.setContactEmailCl(contactClient.getContactEmailCl());
                }
                if (contactClient.getContactMobilePhoneCl() != null) {
                    existingContactClient.setContactMobilePhoneCl(contactClient.getContactMobilePhoneCl());
                }
                if (contactClient.getContactStatusCl() != null) {
                    existingContactClient.setContactStatusCl(contactClient.getContactStatusCl());
                }

                return existingContactClient;
            })
            .map(contactClientRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactClient> findAll() {
        log.debug("Request to get all ContactClients");
        return contactClientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContactClient> findOne(Long id) {
        log.debug("Request to get ContactClient : {}", id);
        return contactClientRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContactClient : {}", id);
        contactClientRepository.deleteById(id);
    }
}
