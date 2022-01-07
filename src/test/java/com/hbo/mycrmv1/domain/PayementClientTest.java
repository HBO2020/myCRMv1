package com.hbo.mycrmv1.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hbo.mycrmv1.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PayementClientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PayementClient.class);
        PayementClient payementClient1 = new PayementClient();
        payementClient1.setId(1L);
        PayementClient payementClient2 = new PayementClient();
        payementClient2.setId(payementClient1.getId());
        assertThat(payementClient1).isEqualTo(payementClient2);
        payementClient2.setId(2L);
        assertThat(payementClient1).isNotEqualTo(payementClient2);
        payementClient1.setId(null);
        assertThat(payementClient1).isNotEqualTo(payementClient2);
    }
}
