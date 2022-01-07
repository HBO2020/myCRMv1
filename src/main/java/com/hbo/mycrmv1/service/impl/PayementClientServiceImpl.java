package com.hbo.mycrmv1.service.impl;

import com.hbo.mycrmv1.domain.PayementClient;
import com.hbo.mycrmv1.repository.PayementClientRepository;
import com.hbo.mycrmv1.service.PayementClientService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PayementClient}.
 */
@Service
@Transactional
public class PayementClientServiceImpl implements PayementClientService {

    private final Logger log = LoggerFactory.getLogger(PayementClientServiceImpl.class);

    private final PayementClientRepository payementClientRepository;

    public PayementClientServiceImpl(PayementClientRepository payementClientRepository) {
        this.payementClientRepository = payementClientRepository;
    }

    @Override
    public PayementClient save(PayementClient payementClient) {
        log.debug("Request to save PayementClient : {}", payementClient);
        return payementClientRepository.save(payementClient);
    }

    @Override
    public Optional<PayementClient> partialUpdate(PayementClient payementClient) {
        log.debug("Request to partially update PayementClient : {}", payementClient);

        return payementClientRepository
            .findById(payementClient.getId())
            .map(existingPayementClient -> {
                if (payementClient.getPayementClIdent() != null) {
                    existingPayementClient.setPayementClIdent(payementClient.getPayementClIdent());
                }
                if (payementClient.getPayementClDate() != null) {
                    existingPayementClient.setPayementClDate(payementClient.getPayementClDate());
                }
                if (payementClient.getPayementClMode() != null) {
                    existingPayementClient.setPayementClMode(payementClient.getPayementClMode());
                }
                if (payementClient.getPayementClEcheance() != null) {
                    existingPayementClient.setPayementClEcheance(payementClient.getPayementClEcheance());
                }
                if (payementClient.getPayementClMontant() != null) {
                    existingPayementClient.setPayementClMontant(payementClient.getPayementClMontant());
                }

                return existingPayementClient;
            })
            .map(payementClientRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PayementClient> findAll() {
        log.debug("Request to get all PayementClients");
        return payementClientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PayementClient> findOne(Long id) {
        log.debug("Request to get PayementClient : {}", id);
        return payementClientRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PayementClient : {}", id);
        payementClientRepository.deleteById(id);
    }
}
