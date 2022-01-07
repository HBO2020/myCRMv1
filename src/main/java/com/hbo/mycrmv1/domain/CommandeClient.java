package com.hbo.mycrmv1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CommandeClient.
 */
@Entity
@Table(name = "commande_client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CommandeClient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cmd_iden_cl")
    private Integer cmdIdenCl;

    @Column(name = "cmd_date_effet_cl")
    private LocalDate cmdDateEffetCl;

    @Column(name = "cmd_date_livraison_cl")
    private LocalDate cmdDateLivraisonCl;

    @OneToMany(mappedBy = "commandeClient")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "articles", "commandeClient" }, allowSetters = true)
    private Set<LigneCmdClient> ligneCmdClients = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "commandeClients", "factureAchats", "contactClients", "civilitecl" }, allowSetters = true)
    private Client client;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ligneLivClients", "commandeClients", "factureVentes" }, allowSetters = true)
    private LivraisonCl livraisonCl;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CommandeClient id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCmdIdenCl() {
        return this.cmdIdenCl;
    }

    public CommandeClient cmdIdenCl(Integer cmdIdenCl) {
        this.setCmdIdenCl(cmdIdenCl);
        return this;
    }

    public void setCmdIdenCl(Integer cmdIdenCl) {
        this.cmdIdenCl = cmdIdenCl;
    }

    public LocalDate getCmdDateEffetCl() {
        return this.cmdDateEffetCl;
    }

    public CommandeClient cmdDateEffetCl(LocalDate cmdDateEffetCl) {
        this.setCmdDateEffetCl(cmdDateEffetCl);
        return this;
    }

    public void setCmdDateEffetCl(LocalDate cmdDateEffetCl) {
        this.cmdDateEffetCl = cmdDateEffetCl;
    }

    public LocalDate getCmdDateLivraisonCl() {
        return this.cmdDateLivraisonCl;
    }

    public CommandeClient cmdDateLivraisonCl(LocalDate cmdDateLivraisonCl) {
        this.setCmdDateLivraisonCl(cmdDateLivraisonCl);
        return this;
    }

    public void setCmdDateLivraisonCl(LocalDate cmdDateLivraisonCl) {
        this.cmdDateLivraisonCl = cmdDateLivraisonCl;
    }

    public Set<LigneCmdClient> getLigneCmdClients() {
        return this.ligneCmdClients;
    }

    public void setLigneCmdClients(Set<LigneCmdClient> ligneCmdClients) {
        if (this.ligneCmdClients != null) {
            this.ligneCmdClients.forEach(i -> i.setCommandeClient(null));
        }
        if (ligneCmdClients != null) {
            ligneCmdClients.forEach(i -> i.setCommandeClient(this));
        }
        this.ligneCmdClients = ligneCmdClients;
    }

    public CommandeClient ligneCmdClients(Set<LigneCmdClient> ligneCmdClients) {
        this.setLigneCmdClients(ligneCmdClients);
        return this;
    }

    public CommandeClient addLigneCmdClient(LigneCmdClient ligneCmdClient) {
        this.ligneCmdClients.add(ligneCmdClient);
        ligneCmdClient.setCommandeClient(this);
        return this;
    }

    public CommandeClient removeLigneCmdClient(LigneCmdClient ligneCmdClient) {
        this.ligneCmdClients.remove(ligneCmdClient);
        ligneCmdClient.setCommandeClient(null);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public CommandeClient client(Client client) {
        this.setClient(client);
        return this;
    }

    public LivraisonCl getLivraisonCl() {
        return this.livraisonCl;
    }

    public void setLivraisonCl(LivraisonCl livraisonCl) {
        this.livraisonCl = livraisonCl;
    }

    public CommandeClient livraisonCl(LivraisonCl livraisonCl) {
        this.setLivraisonCl(livraisonCl);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommandeClient)) {
            return false;
        }
        return id != null && id.equals(((CommandeClient) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommandeClient{" +
            "id=" + getId() +
            ", cmdIdenCl=" + getCmdIdenCl() +
            ", cmdDateEffetCl='" + getCmdDateEffetCl() + "'" +
            ", cmdDateLivraisonCl='" + getCmdDateLivraisonCl() + "'" +
            "}";
    }
}
