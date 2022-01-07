package com.hbo.mycrmv1.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hbo.mycrmv1.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContactFournisseurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactFournisseur.class);
        ContactFournisseur contactFournisseur1 = new ContactFournisseur();
        contactFournisseur1.setId(1L);
        ContactFournisseur contactFournisseur2 = new ContactFournisseur();
        contactFournisseur2.setId(contactFournisseur1.getId());
        assertThat(contactFournisseur1).isEqualTo(contactFournisseur2);
        contactFournisseur2.setId(2L);
        assertThat(contactFournisseur1).isNotEqualTo(contactFournisseur2);
        contactFournisseur1.setId(null);
        assertThat(contactFournisseur1).isNotEqualTo(contactFournisseur2);
    }
}
