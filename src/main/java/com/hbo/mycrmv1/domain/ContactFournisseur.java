package com.hbo.mycrmv1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ContactFournisseur.
 */
@Entity
@Table(name = "contact_fournisseur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContactFournisseur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "contact_fr_name")
    private String contactFrName;

    @Column(name = "contactfr_prenom")
    private String contactfrPrenom;

    @Column(name = "contact_fr_email")
    private String contactFrEmail;

    @Column(name = "contact_fr_mobile_phone")
    private String contactFrMobilePhone;

    @Column(name = "contact_fr_status")
    private String contactFrStatus;

    @ManyToOne
    @JsonIgnoreProperties(value = { "commandeFournisseurs", "factureAchats", "contactFournisseurs", "civilitefr" }, allowSetters = true)
    private Fournisseur fournisseur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ContactFournisseur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactFrName() {
        return this.contactFrName;
    }

    public ContactFournisseur contactFrName(String contactFrName) {
        this.setContactFrName(contactFrName);
        return this;
    }

    public void setContactFrName(String contactFrName) {
        this.contactFrName = contactFrName;
    }

    public String getContactfrPrenom() {
        return this.contactfrPrenom;
    }

    public ContactFournisseur contactfrPrenom(String contactfrPrenom) {
        this.setContactfrPrenom(contactfrPrenom);
        return this;
    }

    public void setContactfrPrenom(String contactfrPrenom) {
        this.contactfrPrenom = contactfrPrenom;
    }

    public String getContactFrEmail() {
        return this.contactFrEmail;
    }

    public ContactFournisseur contactFrEmail(String contactFrEmail) {
        this.setContactFrEmail(contactFrEmail);
        return this;
    }

    public void setContactFrEmail(String contactFrEmail) {
        this.contactFrEmail = contactFrEmail;
    }

    public String getContactFrMobilePhone() {
        return this.contactFrMobilePhone;
    }

    public ContactFournisseur contactFrMobilePhone(String contactFrMobilePhone) {
        this.setContactFrMobilePhone(contactFrMobilePhone);
        return this;
    }

    public void setContactFrMobilePhone(String contactFrMobilePhone) {
        this.contactFrMobilePhone = contactFrMobilePhone;
    }

    public String getContactFrStatus() {
        return this.contactFrStatus;
    }

    public ContactFournisseur contactFrStatus(String contactFrStatus) {
        this.setContactFrStatus(contactFrStatus);
        return this;
    }

    public void setContactFrStatus(String contactFrStatus) {
        this.contactFrStatus = contactFrStatus;
    }

    public Fournisseur getFournisseur() {
        return this.fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public ContactFournisseur fournisseur(Fournisseur fournisseur) {
        this.setFournisseur(fournisseur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactFournisseur)) {
            return false;
        }
        return id != null && id.equals(((ContactFournisseur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactFournisseur{" +
            "id=" + getId() +
            ", contactFrName='" + getContactFrName() + "'" +
            ", contactfrPrenom='" + getContactfrPrenom() + "'" +
            ", contactFrEmail='" + getContactFrEmail() + "'" +
            ", contactFrMobilePhone='" + getContactFrMobilePhone() + "'" +
            ", contactFrStatus='" + getContactFrStatus() + "'" +
            "}";
    }
}
