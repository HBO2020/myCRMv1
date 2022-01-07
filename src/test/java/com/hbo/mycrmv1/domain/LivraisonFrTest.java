package com.hbo.mycrmv1.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hbo.mycrmv1.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LivraisonFrTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LivraisonFr.class);
        LivraisonFr livraisonFr1 = new LivraisonFr();
        livraisonFr1.setId(1L);
        LivraisonFr livraisonFr2 = new LivraisonFr();
        livraisonFr2.setId(livraisonFr1.getId());
        assertThat(livraisonFr1).isEqualTo(livraisonFr2);
        livraisonFr2.setId(2L);
        assertThat(livraisonFr1).isNotEqualTo(livraisonFr2);
        livraisonFr1.setId(null);
        assertThat(livraisonFr1).isNotEqualTo(livraisonFr2);
    }
}
