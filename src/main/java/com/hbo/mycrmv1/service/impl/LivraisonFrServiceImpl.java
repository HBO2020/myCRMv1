package com.hbo.mycrmv1.service.impl;

import com.hbo.mycrmv1.domain.LivraisonFr;
import com.hbo.mycrmv1.repository.LivraisonFrRepository;
import com.hbo.mycrmv1.service.LivraisonFrService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LivraisonFr}.
 */
@Service
@Transactional
public class LivraisonFrServiceImpl implements LivraisonFrService {

    private final Logger log = LoggerFactory.getLogger(LivraisonFrServiceImpl.class);

    private final LivraisonFrRepository livraisonFrRepository;

    public LivraisonFrServiceImpl(LivraisonFrRepository livraisonFrRepository) {
        this.livraisonFrRepository = livraisonFrRepository;
    }

    @Override
    public LivraisonFr save(LivraisonFr livraisonFr) {
        log.debug("Request to save LivraisonFr : {}", livraisonFr);
        return livraisonFrRepository.save(livraisonFr);
    }

    @Override
    public Optional<LivraisonFr> partialUpdate(LivraisonFr livraisonFr) {
        log.debug("Request to partially update LivraisonFr : {}", livraisonFr);

        return livraisonFrRepository
            .findById(livraisonFr.getId())
            .map(existingLivraisonFr -> {
                if (livraisonFr.getBonLivIdent() != null) {
                    existingLivraisonFr.setBonLivIdent(livraisonFr.getBonLivIdent());
                }
                if (livraisonFr.getLivFrDate() != null) {
                    existingLivraisonFr.setLivFrDate(livraisonFr.getLivFrDate());
                }
                if (livraisonFr.getLivFrDateUpdate() != null) {
                    existingLivraisonFr.setLivFrDateUpdate(livraisonFr.getLivFrDateUpdate());
                }
                if (livraisonFr.getLivDateEffet() != null) {
                    existingLivraisonFr.setLivDateEffet(livraisonFr.getLivDateEffet());
                }
                if (livraisonFr.getBonLivTotal() != null) {
                    existingLivraisonFr.setBonLivTotal(livraisonFr.getBonLivTotal());
                }

                return existingLivraisonFr;
            })
            .map(livraisonFrRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LivraisonFr> findAll() {
        log.debug("Request to get all LivraisonFrs");
        return livraisonFrRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LivraisonFr> findOne(Long id) {
        log.debug("Request to get LivraisonFr : {}", id);
        return livraisonFrRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LivraisonFr : {}", id);
        livraisonFrRepository.deleteById(id);
    }
}
