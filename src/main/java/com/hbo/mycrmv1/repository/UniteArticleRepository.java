package com.hbo.mycrmv1.repository;

import com.hbo.mycrmv1.domain.UniteArticle;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UniteArticle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UniteArticleRepository extends JpaRepository<UniteArticle, Long> {}
