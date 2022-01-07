package com.hbo.mycrmv1.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hbo.mycrmv1.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CiviliteClientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CiviliteClient.class);
        CiviliteClient civiliteClient1 = new CiviliteClient();
        civiliteClient1.setId(1L);
        CiviliteClient civiliteClient2 = new CiviliteClient();
        civiliteClient2.setId(civiliteClient1.getId());
        assertThat(civiliteClient1).isEqualTo(civiliteClient2);
        civiliteClient2.setId(2L);
        assertThat(civiliteClient1).isNotEqualTo(civiliteClient2);
        civiliteClient1.setId(null);
        assertThat(civiliteClient1).isNotEqualTo(civiliteClient2);
    }
}
