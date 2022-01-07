package com.hbo.mycrmv1.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hbo.mycrmv1.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LigneCmdFournisseurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LigneCmdFournisseur.class);
        LigneCmdFournisseur ligneCmdFournisseur1 = new LigneCmdFournisseur();
        ligneCmdFournisseur1.setId(1L);
        LigneCmdFournisseur ligneCmdFournisseur2 = new LigneCmdFournisseur();
        ligneCmdFournisseur2.setId(ligneCmdFournisseur1.getId());
        assertThat(ligneCmdFournisseur1).isEqualTo(ligneCmdFournisseur2);
        ligneCmdFournisseur2.setId(2L);
        assertThat(ligneCmdFournisseur1).isNotEqualTo(ligneCmdFournisseur2);
        ligneCmdFournisseur1.setId(null);
        assertThat(ligneCmdFournisseur1).isNotEqualTo(ligneCmdFournisseur2);
    }
}
