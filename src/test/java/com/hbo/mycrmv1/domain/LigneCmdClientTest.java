package com.hbo.mycrmv1.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hbo.mycrmv1.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LigneCmdClientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LigneCmdClient.class);
        LigneCmdClient ligneCmdClient1 = new LigneCmdClient();
        ligneCmdClient1.setId(1L);
        LigneCmdClient ligneCmdClient2 = new LigneCmdClient();
        ligneCmdClient2.setId(ligneCmdClient1.getId());
        assertThat(ligneCmdClient1).isEqualTo(ligneCmdClient2);
        ligneCmdClient2.setId(2L);
        assertThat(ligneCmdClient1).isNotEqualTo(ligneCmdClient2);
        ligneCmdClient1.setId(null);
        assertThat(ligneCmdClient1).isNotEqualTo(ligneCmdClient2);
    }
}
