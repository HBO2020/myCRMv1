package com.hbo.mycrmv1.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.hbo.mycrmv1.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CartsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Carts.class);
        Carts carts1 = new Carts();
        carts1.setId(1L);
        Carts carts2 = new Carts();
        carts2.setId(carts1.getId());
        assertThat(carts1).isEqualTo(carts2);
        carts2.setId(2L);
        assertThat(carts1).isNotEqualTo(carts2);
        carts1.setId(null);
        assertThat(carts1).isNotEqualTo(carts2);
    }
}
