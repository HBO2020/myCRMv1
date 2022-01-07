package com.hbo.mycrmv1.service.impl;

import com.hbo.mycrmv1.domain.Article;
import com.hbo.mycrmv1.repository.ArticleRepository;
import com.hbo.mycrmv1.service.ArticleService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Article}.
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article save(Article article) {
        log.debug("Request to save Article : {}", article);
        return articleRepository.save(article);
    }

    @Override
    public Optional<Article> partialUpdate(Article article) {
        log.debug("Request to partially update Article : {}", article);

        return articleRepository
            .findById(article.getId())
            .map(existingArticle -> {
                if (article.getArtclIden() != null) {
                    existingArticle.setArtclIden(article.getArtclIden());
                }
                if (article.getArtclReference() != null) {
                    existingArticle.setArtclReference(article.getArtclReference());
                }
                if (article.getArtclDesignation() != null) {
                    existingArticle.setArtclDesignation(article.getArtclDesignation());
                }
                if (article.getArtclQnStock() != null) {
                    existingArticle.setArtclQnStock(article.getArtclQnStock());
                }
                if (article.getArtclImg() != null) {
                    existingArticle.setArtclImg(article.getArtclImg());
                }
                if (article.getArtclImgContentType() != null) {
                    existingArticle.setArtclImgContentType(article.getArtclImgContentType());
                }
                if (article.getArtclSerie() != null) {
                    existingArticle.setArtclSerie(article.getArtclSerie());
                }
                if (article.getArtclPrixAchat() != null) {
                    existingArticle.setArtclPrixAchat(article.getArtclPrixAchat());
                }
                if (article.getArtclPxAchatTotal() != null) {
                    existingArticle.setArtclPxAchatTotal(article.getArtclPxAchatTotal());
                }
                if (article.getArtclPxVenteTotal() != null) {
                    existingArticle.setArtclPxVenteTotal(article.getArtclPxVenteTotal());
                }

                return existingArticle;
            })
            .map(articleRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Article> findAll(Pageable pageable) {
        log.debug("Request to get all Articles");
        return articleRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Article> findOne(Long id) {
        log.debug("Request to get Article : {}", id);
        return articleRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Article : {}", id);
        articleRepository.deleteById(id);
    }
}
