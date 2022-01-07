package com.hbo.mycrmv1.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hbo.mycrmv1.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PayementFournisseurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayementFournisseur.class);
        PayementFournisseur payementFournisseur1 = new PayementFournisseur();
        payementFournisseur1.setId(1L);
        PayementFournisseur payementFournisseur2 = new PayementFournisseur();
        payementFournisseur2.setId(payementFournisseur1.getId());
        assertThat(payementFournisseur1).isEqualTo(payementFournisseur2);
        payementFournisseur2.setId(2L);
        assertThat(payementFournisseur1).isNotEqualTo(payementFournisseur2);
        payementFournisseur1.setId(null);
        assertThat(payementFournisseur1).isNotEqualTo(payementFournisseur2);
    }
}
