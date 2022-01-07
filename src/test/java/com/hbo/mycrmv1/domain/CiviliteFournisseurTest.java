package com.hbo.mycrmv1.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hbo.mycrmv1.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CiviliteFournisseurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CiviliteFournisseur.class);
        CiviliteFournisseur civiliteFournisseur1 = new CiviliteFournisseur();
        civiliteFournisseur1.setId(1L);
        CiviliteFournisseur civiliteFournisseur2 = new CiviliteFournisseur();
        civiliteFournisseur2.setId(civiliteFournisseur1.getId());
        assertThat(civiliteFournisseur1).isEqualTo(civiliteFournisseur2);
        civiliteFournisseur2.setId(2L);
        assertThat(civiliteFournisseur1).isNotEqualTo(civiliteFournisseur2);
        civiliteFournisseur1.setId(null);
        assertThat(civiliteFournisseur1).isNotEqualTo(civiliteFournisseur2);
    }
}
