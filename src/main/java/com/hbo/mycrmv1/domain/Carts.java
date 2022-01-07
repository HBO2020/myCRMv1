package com.hbo.mycrmv1.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * not an ignored comment
 */
@Schema(description = "not an ignored comment")
@Entity
@Table(name = "carts")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Carts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cart_is_empty")
    private Boolean cartIsEmpty;

    @Column(name = "cart_user_email")
    private String cartUserEmail;

    @Column(name = "cart_list_product")
    private String cartListProduct;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Carts id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getCartIsEmpty() {
        return this.cartIsEmpty;
    }

    public Carts cartIsEmpty(Boolean cartIsEmpty) {
        this.setCartIsEmpty(cartIsEmpty);
        return this;
    }

    public void setCartIsEmpty(Boolean cartIsEmpty) {
        this.cartIsEmpty = cartIsEmpty;
    }

    public String getCartUserEmail() {
        return this.cartUserEmail;
    }

    public Carts cartUserEmail(String cartUserEmail) {
        this.setCartUserEmail(cartUserEmail);
        return this;
    }

    public void setCartUserEmail(String cartUserEmail) {
        this.cartUserEmail = cartUserEmail;
    }

    public String getCartListProduct() {
        return this.cartListProduct;
    }

    public Carts cartListProduct(String cartListProduct) {
        this.setCartListProduct(cartListProduct);
        return this;
    }

    public void setCartListProduct(String cartListProduct) {
        this.cartListProduct = cartListProduct;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Carts)) {
            return false;
        }
        return id != null && id.equals(((Carts) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Carts{" +
            "id=" + getId() +
            ", cartIsEmpty='" + getCartIsEmpty() + "'" +
            ", cartUserEmail='" + getCartUserEmail() + "'" +
            ", cartListProduct='" + getCartListProduct() + "'" +
            "}";
    }
}
