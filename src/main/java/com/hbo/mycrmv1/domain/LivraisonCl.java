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
 * A LivraisonCl.
 */
@Entity
@Table(name = "livraison_cl")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LivraisonCl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bon_liv_ident_cl")
    private Integer bonLivIdentCl;

    @Column(name = "liv_date_cl")
    private LocalDate livDateCl;

    @Column(name = "liv_date_update_cl")
    private LocalDate livDateUpdateCl;

    @Column(name = "liv_date_effet_cl")
    private LocalDate livDateEffetCl;

    @Column(name = "bon_liv_total_cl")
    private Double bonLivTotalCl;

    @OneToMany(mappedBy = "livraisonCl")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "articles", "livraisonCl" }, allowSetters = true)
    private Set<LigneLivClient> ligneLivClients = new HashSet<>();

    @OneToMany(mappedBy = "livraisonCl")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ligneCmdClients", "client", "livraisonCl" }, allowSetters = true)
    private Set<CommandeClient> commandeClients = new HashSet<>();

    @OneToMany(mappedBy = "livraisonCl")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "livraisonCl" }, allowSetters = true)
    private Set<FactureVente> factureVentes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LivraisonCl id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBonLivIdentCl() {
        return this.bonLivIdentCl;
    }

    public LivraisonCl bonLivIdentCl(Integer bonLivIdentCl) {
        this.setBonLivIdentCl(bonLivIdentCl);
        return this;
    }

    public void setBonLivIdentCl(Integer bonLivIdentCl) {
        this.bonLivIdentCl = bonLivIdentCl;
    }

    public LocalDate getLivDateCl() {
        return this.livDateCl;
    }

    public LivraisonCl livDateCl(LocalDate livDateCl) {
        this.setLivDateCl(livDateCl);
        return this;
    }

    public void setLivDateCl(LocalDate livDateCl) {
        this.livDateCl = livDateCl;
    }

    public LocalDate getLivDateUpdateCl() {
        return this.livDateUpdateCl;
    }

    public LivraisonCl livDateUpdateCl(LocalDate livDateUpdateCl) {
        this.setLivDateUpdateCl(livDateUpdateCl);
        return this;
    }

    public void setLivDateUpdateCl(LocalDate livDateUpdateCl) {
        this.livDateUpdateCl = livDateUpdateCl;
    }

    public LocalDate getLivDateEffetCl() {
        return this.livDateEffetCl;
    }

    public LivraisonCl livDateEffetCl(LocalDate livDateEffetCl) {
        this.setLivDateEffetCl(livDateEffetCl);
        return this;
    }

    public void setLivDateEffetCl(LocalDate livDateEffetCl) {
        this.livDateEffetCl = livDateEffetCl;
    }

    public Double getBonLivTotalCl() {
        return this.bonLivTotalCl;
    }

    public LivraisonCl bonLivTotalCl(Double bonLivTotalCl) {
        this.setBonLivTotalCl(bonLivTotalCl);
        return this;
    }

    public void setBonLivTotalCl(Double bonLivTotalCl) {
        this.bonLivTotalCl = bonLivTotalCl;
    }

    public Set<LigneLivClient> getLigneLivClients() {
        return this.ligneLivClients;
    }

    public void setLigneLivClients(Set<LigneLivClient> ligneLivClients) {
        if (this.ligneLivClients != null) {
            this.ligneLivClients.forEach(i -> i.setLivraisonCl(null));
        }
        if (ligneLivClients != null) {
            ligneLivClients.forEach(i -> i.setLivraisonCl(this));
        }
        this.ligneLivClients = ligneLivClients;
    }

    public LivraisonCl ligneLivClients(Set<LigneLivClient> ligneLivClients) {
        this.setLigneLivClients(ligneLivClients);
        return this;
    }

    public LivraisonCl addLigneLivClient(LigneLivClient ligneLivClient) {
        this.ligneLivClients.add(ligneLivClient);
        ligneLivClient.setLivraisonCl(this);
        return this;
    }

    public LivraisonCl removeLigneLivClient(LigneLivClient ligneLivClient) {
        this.ligneLivClients.remove(ligneLivClient);
        ligneLivClient.setLivraisonCl(null);
        return this;
    }

    public Set<CommandeClient> getCommandeClients() {
        return this.commandeClients;
    }

    public void setCommandeClients(Set<CommandeClient> commandeClients) {
        if (this.commandeClients != null) {
            this.commandeClients.forEach(i -> i.setLivraisonCl(null));
        }
        if (commandeClients != null) {
            commandeClients.forEach(i -> i.setLivraisonCl(this));
        }
        this.commandeClients = commandeClients;
    }

    public LivraisonCl commandeClients(Set<CommandeClient> commandeClients) {
        this.setCommandeClients(commandeClients);
        return this;
    }

    public LivraisonCl addCommandeClient(CommandeClient commandeClient) {
        this.commandeClients.add(commandeClient);
        commandeClient.setLivraisonCl(this);
        return this;
    }

    public LivraisonCl removeCommandeClient(CommandeClient commandeClient) {
        this.commandeClients.remove(commandeClient);
        commandeClient.setLivraisonCl(null);
        return this;
    }

    public Set<FactureVente> getFactureVentes() {
        return this.factureVentes;
    }

    public void setFactureVentes(Set<FactureVente> factureVentes) {
        if (this.factureVentes != null) {
            this.factureVentes.forEach(i -> i.setLivraisonCl(null));
        }
        if (factureVentes != null) {
            factureVentes.forEach(i -> i.setLivraisonCl(this));
        }
        this.factureVentes = factureVentes;
    }

    public LivraisonCl factureVentes(Set<FactureVente> factureVentes) {
        this.setFactureVentes(factureVentes);
        return this;
    }

    public LivraisonCl addFactureVente(FactureVente factureVente) {
        this.factureVentes.add(factureVente);
        factureVente.setLivraisonCl(this);
        return this;
    }

    public LivraisonCl removeFactureVente(FactureVente factureVente) {
        this.factureVentes.remove(factureVente);
        factureVente.setLivraisonCl(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LivraisonCl)) {
            return false;
        }
        return id != null && id.equals(((LivraisonCl) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LivraisonCl{" +
            "id=" + getId() +
            ", bonLivIdentCl=" + getBonLivIdentCl() +
            ", livDateCl='" + getLivDateCl() + "'" +
            ", livDateUpdateCl='" + getLivDateUpdateCl() + "'" +
            ", livDateEffetCl='" + getLivDateEffetCl() + "'" +
            ", bonLivTotalCl=" + getBonLivTotalCl() +
            "}";
    }
}
