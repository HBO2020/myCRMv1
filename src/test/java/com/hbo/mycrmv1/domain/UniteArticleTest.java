package com.hbo.mycrmv1.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hbo.mycrmv1.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UniteArticleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UniteArticle.class);
        UniteArticle uniteArticle1 = new UniteArticle();
        uniteArticle1.setId(1L);
        UniteArticle uniteArticle2 = new UniteArticle();
        uniteArticle2.setId(uniteArticle1.getId());
        assertThat(uniteArticle1).isEqualTo(uniteArticle2);
        uniteArticle2.setId(2L);
        assertThat(uniteArticle1).isNotEqualTo(uniteArticle2);
        uniteArticle1.setId(null);
        assertThat(uniteArticle1).isNotEqualTo(uniteArticle2);
    }
}
