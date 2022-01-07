package com.hbo.mycrmv1.service.impl;

import com.hbo.mycrmv1.domain.UniteArticle;
import com.hbo.mycrmv1.repository.UniteArticleRepository;
import com.hbo.mycrmv1.service.UniteArticleService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UniteArticle}.
 */
@Service
@Transactional
public class UniteArticleServiceImpl implements UniteArticleService {

    private final Logger log = LoggerFactory.getLogger(UniteArticleServiceImpl.class);

    private final UniteArticleRepository uniteArticleRepository;

    public UniteArticleServiceImpl(UniteArticleRepository uniteArticleRepository) {
        this.uniteArticleRepository = uniteArticleRepository;
    }

    @Override
    public UniteArticle save(UniteArticle uniteArticle) {
        log.debug("Request to save UniteArticle : {}", uniteArticle);
        return uniteArticleRepository.save(uniteArticle);
    }

    @Override
    public Optional<UniteArticle> partialUpdate(UniteArticle uniteArticle) {
        log.debug("Request to partially update UniteArticle : {}", uniteArticle);

        return uniteArticleRepository
            .findById(uniteArticle.getId())
            .map(existingUniteArticle -> {
                if (uniteArticle.getUniteCode() != null) {
                    existingUniteArticle.setUniteCode(uniteArticle.getUniteCode());
                }
                if (uniteArticle.getUniteLibelle() != null) {
                    existingUniteArticle.setUniteLibelle(uniteArticle.getUniteLibelle());
                }
                if (uniteArticle.getUniteOption() != null) {
                    existingUniteArticle.setUniteOption(uniteArticle.getUniteOption());
                }

                return existingUniteArticle;
            })
            .map(uniteArticleRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UniteArticle> findAll() {
        log.debug("Request to get all UniteArticles");
        return uniteArticleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UniteArticle> findOne(Long id) {
        log.debug("Request to get UniteArticle : {}", id);
        return uniteArticleRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UniteArticle : {}", id);
        uniteArticleRepository.deleteById(id);
    }
}
