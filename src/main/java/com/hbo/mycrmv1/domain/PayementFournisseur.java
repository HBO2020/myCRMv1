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
 * A PayementFournisseur.
 */
@Entity
@Table(name = "payement_fournisseur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PayementFournisseur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "payement_fr_ident")
    private Integer payementFrIdent;

    @Column(name = "payement_fr_date")
    private LocalDate payementFrDate;

    @Column(name = "payement_fr_mode")
    private String payementFrMode;

    @Column(name = "payement_fr_echeance")
    private LocalDate payementFrEcheance;

    @Column(name = "payement_fr_montant")
    private Double payementFrMontant;

    @OneToMany(mappedBy = "payementFr")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fournisseur", "payementFr", "livraisonFr", "client", "payementCl" }, allowSetters = true)
    private Set<FactureAchat> factureAchats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PayementFournisseur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPayementFrIdent() {
        return this.payementFrIdent;
    }

    public PayementFournisseur payementFrIdent(Integer payementFrIdent) {
        this.setPayementFrIdent(payementFrIdent);
        return this;
    }

    public void setPayementFrIdent(Integer payementFrIdent) {
        this.payementFrIdent = payementFrIdent;
    }

    public LocalDate getPayementFrDate() {
        return this.payementFrDate;
    }

    public PayementFournisseur payementFrDate(LocalDate payementFrDate) {
        this.setPayementFrDate(payementFrDate);
        return this;
    }

    public void setPayementFrDate(LocalDate payementFrDate) {
        this.payementFrDate = payementFrDate;
    }

    public String getPayementFrMode() {
        return this.payementFrMode;
    }

    public PayementFournisseur payementFrMode(String payementFrMode) {
        this.setPayementFrMode(payementFrMode);
        return this;
    }

    public void setPayementFrMode(String payementFrMode) {
        this.payementFrMode = payementFrMode;
    }

    public LocalDate getPayementFrEcheance() {
        return this.payementFrEcheance;
    }

    public PayementFournisseur payementFrEcheance(LocalDate payementFrEcheance) {
        this.setPayementFrEcheance(payementFrEcheance);
        return this;
    }

    public void setPayementFrEcheance(LocalDate payementFrEcheance) {
        this.payementFrEcheance = payementFrEcheance;
    }

    public Double getPayementFrMontant() {
        return this.payementFrMontant;
    }

    public PayementFournisseur payementFrMontant(Double payementFrMontant) {
        this.setPayementFrMontant(payementFrMontant);
        return this;
    }

    public void setPayementFrMontant(Double payementFrMontant) {
        this.payementFrMontant = payementFrMontant;
    }

    public Set<FactureAchat> getFactureAchats() {
        return this.factureAchats;
    }

    public void setFactureAchats(Set<FactureAchat> factureAchats) {
        if (this.factureAchats != null) {
            this.factureAchats.forEach(i -> i.setPayementFr(null));
        }
        if (factureAchats != null) {
            factureAchats.forEach(i -> i.setPayementFr(this));
        }
        this.factureAchats = factureAchats;
    }

    public PayementFournisseur factureAchats(Set<FactureAchat> factureAchats) {
        this.setFactureAchats(factureAchats);
        return this;
    }

    public PayementFournisseur addFactureAchat(FactureAchat factureAchat) {
        this.factureAchats.add(factureAchat);
        factureAchat.setPayementFr(this);
        return this;
    }

    public PayementFournisseur removeFactureAchat(FactureAchat factureAchat) {
        this.factureAchats.remove(factureAchat);
        factureAchat.setPayementFr(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PayementFournisseur)) {
            return false;
        }
        return id != null && id.equals(((PayementFournisseur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PayementFournisseur{" +
            "id=" + getId() +
            ", payementFrIdent=" + getPayementFrIdent() +
            ", payementFrDate='" + getPayementFrDate() + "'" +
            ", payementFrMode='" + getPayementFrMode() + "'" +
            ", payementFrEcheance='" + getPayementFrEcheance() + "'" +
            ", payementFrMontant=" + getPayementFrMontant() +
            "}";
    }
}
