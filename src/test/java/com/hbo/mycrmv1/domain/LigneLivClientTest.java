package com.hbo.mycrmv1.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hbo.mycrmv1.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LigneLivClientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LigneLivClient.class);
        LigneLivClient ligneLivClient1 = new LigneLivClient();
        ligneLivClient1.setId(1L);
        LigneLivClient ligneLivClient2 = new LigneLivClient();
        ligneLivClient2.setId(ligneLivClient1.getId());
        assertThat(ligneLivClient1).isEqualTo(ligneLivClient2);
        ligneLivClient2.setId(2L);
        assertThat(ligneLivClient1).isNotEqualTo(ligneLivClient2);
        ligneLivClient1.setId(null);
        assertThat(ligneLivClient1).isNotEqualTo(ligneLivClient2);
    }
}
