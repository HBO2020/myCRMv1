package com.hbo.mycrmv1.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hbo.mycrmv1.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LigneLivFournisseurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LigneLivFournisseur.class);
        LigneLivFournisseur ligneLivFournisseur1 = new LigneLivFournisseur();
        ligneLivFournisseur1.setId(1L);
        LigneLivFournisseur ligneLivFournisseur2 = new LigneLivFournisseur();
        ligneLivFournisseur2.setId(ligneLivFournisseur1.getId());
        assertThat(ligneLivFournisseur1).isEqualTo(ligneLivFournisseur2);
        ligneLivFournisseur2.setId(2L);
        assertThat(ligneLivFournisseur1).isNotEqualTo(ligneLivFournisseur2);
        ligneLivFournisseur1.setId(null);
        assertThat(ligneLivFournisseur1).isNotEqualTo(ligneLivFournisseur2);
    }
}
