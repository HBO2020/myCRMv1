package com.hbo.mycrmv1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ContactClient.
 */
@Entity
@Table(name = "contact_client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContactClient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "contact_name_cl")
    private String contactNameCl;

    @Column(name = "contact_prenom_cl")
    private String contactPrenomCl;

    @Column(name = "contact_email_cl")
    private String contactEmailCl;

    @Column(name = "contact_mobile_phone_cl")
    private String contactMobilePhoneCl;

    @Column(name = "contact_status_cl")
    private String contactStatusCl;

    @ManyToOne
    @JsonIgnoreProperties(value = { "commandeClients", "factureAchats", "contactClients", "civilitecl" }, allowSetters = true)
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ContactClient id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactNameCl() {
        return this.contactNameCl;
    }

    public ContactClient contactNameCl(String contactNameCl) {
        this.setContactNameCl(contactNameCl);
        return this;
    }

    public void setContactNameCl(String contactNameCl) {
        this.contactNameCl = contactNameCl;
    }

    public String getContactPrenomCl() {
        return this.contactPrenomCl;
    }

    public ContactClient contactPrenomCl(String contactPrenomCl) {
        this.setContactPrenomCl(contactPrenomCl);
        return this;
    }

    public void setContactPrenomCl(String contactPrenomCl) {
        this.contactPrenomCl = contactPrenomCl;
    }

    public String getContactEmailCl() {
        return this.contactEmailCl;
    }

    public ContactClient contactEmailCl(String contactEmailCl) {
        this.setContactEmailCl(contactEmailCl);
        return this;
    }

    public void setContactEmailCl(String contactEmailCl) {
        this.contactEmailCl = contactEmailCl;
    }

    public String getContactMobilePhoneCl() {
        return this.contactMobilePhoneCl;
    }

    public ContactClient contactMobilePhoneCl(String contactMobilePhoneCl) {
        this.setContactMobilePhoneCl(contactMobilePhoneCl);
        return this;
    }

    public void setContactMobilePhoneCl(String contactMobilePhoneCl) {
        this.contactMobilePhoneCl = contactMobilePhoneCl;
    }

    public String getContactStatusCl() {
        return this.contactStatusCl;
    }

    public ContactClient contactStatusCl(String contactStatusCl) {
        this.setContactStatusCl(contactStatusCl);
        return this;
    }

    public void setContactStatusCl(String contactStatusCl) {
        this.contactStatusCl = contactStatusCl;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ContactClient client(Client client) {
        this.setClient(client);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactClient)) {
            return false;
        }
        return id != null && id.equals(((ContactClient) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactClient{" +
            "id=" + getId() +
            ", contactNameCl='" + getContactNameCl() + "'" +
            ", contactPrenomCl='" + getContactPrenomCl() + "'" +
            ", contactEmailCl='" + getContactEmailCl() + "'" +
            ", contactMobilePhoneCl='" + getContactMobilePhoneCl() + "'" +
            ", contactStatusCl='" + getContactStatusCl() + "'" +
            "}";
    }
}
