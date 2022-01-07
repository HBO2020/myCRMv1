package com.hbo.mycrmv1.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hbo.mycrmv1.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContactClientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactClient.class);
        ContactClient contactClient1 = new ContactClient();
        contactClient1.setId(1L);
        ContactClient contactClient2 = new ContactClient();
        contactClient2.setId(contactClient1.getId());
        assertThat(contactClient1).isEqualTo(contactClient2);
        contactClient2.setId(2L);
        assertThat(contactClient1).isNotEqualTo(contactClient2);
        contactClient1.setId(null);
        assertThat(contactClient1).isNotEqualTo(contactClient2);
    }
}
