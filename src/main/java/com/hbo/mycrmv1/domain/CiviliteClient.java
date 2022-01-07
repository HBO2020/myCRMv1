package com.hbo.mycrmv1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CiviliteClient.
 */
@Entity
@Table(name = "civilite_client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CiviliteClient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "civilite_libelle_cl")
    private String civiliteLibelleCl;

    @Column(name = "civilite_code_cl")
    private Integer civiliteCodeCl;

    @OneToMany(mappedBy = "civilitecl")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "commandeClients", "factureAchats", "contactClients", "civilitecl" }, allowSetters = true)
    private Set<Client> clients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CiviliteClient id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCiviliteLibelleCl() {
        return this.civiliteLibelleCl;
    }

    public CiviliteClient civiliteLibelleCl(String civiliteLibelleCl) {
        this.setCiviliteLibelleCl(civiliteLibelleCl);
        return this;
    }

    public void setCiviliteLibelleCl(String civiliteLibelleCl) {
        this.civiliteLibelleCl = civiliteLibelleCl;
    }

    public Integer getCiviliteCodeCl() {
        return this.civiliteCodeCl;
    }

    public CiviliteClient civiliteCodeCl(Integer civiliteCodeCl) {
        this.setCiviliteCodeCl(civiliteCodeCl);
        return this;
    }

    public void setCiviliteCodeCl(Integer civiliteCodeCl) {
        this.civiliteCodeCl = civiliteCodeCl;
    }

    public Set<Client> getClients() {
        return this.clients;
    }

    public void setClients(Set<Client> clients) {
        if (this.clients != null) {
            this.clients.forEach(i -> i.setCivilitecl(null));
        }
        if (clients != null) {
            clients.forEach(i -> i.setCivilitecl(this));
        }
        this.clients = clients;
    }

    public CiviliteClient clients(Set<Client> clients) {
        this.setClients(clients);
        return this;
    }

    public CiviliteClient addClient(Client client) {
        this.clients.add(client);
        client.setCivilitecl(this);
        return this;
    }

    public CiviliteClient removeClient(Client client) {
        this.clients.remove(client);
        client.setCivilitecl(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CiviliteClient)) {
            return false;
        }
        return id != null && id.equals(((CiviliteClient) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CiviliteClient{" +
            "id=" + getId() +
            ", civiliteLibelleCl='" + getCiviliteLibelleCl() + "'" +
            ", civiliteCodeCl=" + getCiviliteCodeCl() +
            "}";
    }
}
