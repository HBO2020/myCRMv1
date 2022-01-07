package com.hbo.mycrmv1.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hbo.mycrmv1.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LivraisonClTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LivraisonCl.class);
        LivraisonCl livraisonCl1 = new LivraisonCl();
        livraisonCl1.setId(1L);
        LivraisonCl livraisonCl2 = new LivraisonCl();
        livraisonCl2.setId(livraisonCl1.getId());
        assertThat(livraisonCl1).isEqualTo(livraisonCl2);
        livraisonCl2.setId(2L);
        assertThat(livraisonCl1).isNotEqualTo(livraisonCl2);
        livraisonCl1.setId(null);
        assertThat(livraisonCl1).isNotEqualTo(livraisonCl2);
    }
}
