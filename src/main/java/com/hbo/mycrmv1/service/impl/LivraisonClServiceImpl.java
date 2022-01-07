package com.hbo.mycrmv1.service.impl;

import com.hbo.mycrmv1.domain.LivraisonCl;
import com.hbo.mycrmv1.repository.LivraisonClRepository;
import com.hbo.mycrmv1.service.LivraisonClService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LivraisonCl}.
 */
@Service
@Transactional
public class LivraisonClServiceImpl implements LivraisonClService {

    private final Logger log = LoggerFactory.getLogger(LivraisonClServiceImpl.class);

    private final LivraisonClRepository livraisonClRepository;

    public LivraisonClServiceImpl(LivraisonClRepository livraisonClRepository) {
        this.livraisonClRepository = livraisonClRepository;
    }

    @Override
    public LivraisonCl save(LivraisonCl livraisonCl) {
        log.debug("Request to save LivraisonCl : {}", livraisonCl);
        return livraisonClRepository.save(livraisonCl);
    }

    @Override
    public Optional<LivraisonCl> partialUpdate(LivraisonCl livraisonCl) {
        log.debug("Request to partially update LivraisonCl : {}", livraisonCl);

        return livraisonClRepository
            .findById(livraisonCl.getId())
            .map(existingLivraisonCl -> {
                if (livraisonCl.getBonLivIdentCl() != null) {
                    existingLivraisonCl.setBonLivIdentCl(livraisonCl.getBonLivIdentCl());
                }
                if (livraisonCl.getLivDateCl() != null) {
                    existingLivraisonCl.setLivDateCl(livraisonCl.getLivDateCl());
                }
                if (livraisonCl.getLivDateUpdateCl() != null) {
                    existingLivraisonCl.setLivDateUpdateCl(livraisonCl.getLivDateUpdateCl());
                }
                if (livraisonCl.getLivDateEffetCl() != null) {
                    existingLivraisonCl.setLivDateEffetCl(livraisonCl.getLivDateEffetCl());
                }
                if (livraisonCl.getBonLivTotalCl() != null) {
                    existingLivraisonCl.setBonLivTotalCl(livraisonCl.getBonLivTotalCl());
                }

                return existingLivraisonCl;
            })
            .map(livraisonClRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LivraisonCl> findAll() {
        log.debug("Request to get all LivraisonCls");
        return livraisonClRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LivraisonCl> findOne(Long id) {
        log.debug("Request to get LivraisonCl : {}", id);
        return livraisonClRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LivraisonCl : {}", id);
        livraisonClRepository.deleteById(id);
    }
}
