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
 * A PayementClient.
 */
@Entity
@Table(name = "payement_client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PayementClient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "payement_cl_ident")
    private Integer payementClIdent;

    @Column(name = "payement_cl_date")
    private LocalDate payementClDate;

    @Column(name = "payement_cl_mode")
    private String payementClMode;

    @Column(name = "payement_cl_echeance")
    private LocalDate payementClEcheance;

    @Column(name = "payement_cl_montant")
    private Double payementClMontant;

    @OneToMany(mappedBy = "payementCl")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fournisseur", "payementFr", "livraisonFr", "client", "payementCl" }, allowSetters = true)
    private Set<FactureAchat> factureAchats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PayementClient id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPayementClIdent() {
        return this.payementClIdent;
    }

    public PayementClient payementClIdent(Integer payementClIdent) {
        this.setPayementClIdent(payementClIdent);
        return this;
    }

    public void setPayementClIdent(Integer payementClIdent) {
        this.payementClIdent = payementClIdent;
    }

    public LocalDate getPayementClDate() {
        return this.payementClDate;
    }

    public PayementClient payementClDate(LocalDate payementClDate) {
        this.setPayementClDate(payementClDate);
        return this;
    }

    public void setPayementClDate(LocalDate payementClDate) {
        this.payementClDate = payementClDate;
    }

    public String getPayementClMode() {
        return this.payementClMode;
    }

    public PayementClient payementClMode(String payementClMode) {
        this.setPayementClMode(payementClMode);
        return this;
    }

    public void setPayementClMode(String payementClMode) {
        this.payementClMode = payementClMode;
    }

    public LocalDate getPayementClEcheance() {
        return this.payementClEcheance;
    }

    public PayementClient payementClEcheance(LocalDate payementClEcheance) {
        this.setPayementClEcheance(payementClEcheance);
        return this;
    }

    public void setPayementClEcheance(LocalDate payementClEcheance) {
        this.payementClEcheance = payementClEcheance;
    }

    public Double getPayementClMontant() {
        return this.payementClMontant;
    }

    public PayementClient payementClMontant(Double payementClMontant) {
        this.setPayementClMontant(payementClMontant);
        return this;
    }

    public void setPayementClMontant(Double payementClMontant) {
        this.payementClMontant = payementClMontant;
    }

    public Set<FactureAchat> getFactureAchats() {
        return this.factureAchats;
    }

    public void setFactureAchats(Set<FactureAchat> factureAchats) {
        if (this.factureAchats != null) {
            this.factureAchats.forEach(i -> i.setPayementCl(null));
        }
        if (factureAchats != null) {
            factureAchats.forEach(i -> i.setPayementCl(this));
        }
        this.factureAchats = factureAchats;
    }

    public PayementClient factureAchats(Set<FactureAchat> factureAchats) {
        this.setFactureAchats(factureAchats);
        return this;
    }

    public PayementClient addFactureAchat(FactureAchat factureAchat) {
        this.factureAchats.add(factureAchat);
        factureAchat.setPayementCl(this);
        return this;
    }

    public PayementClient removeFactureAchat(FactureAchat factureAchat) {
        this.factureAchats.remove(factureAchat);
        factureAchat.setPayementCl(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PayementClient)) {
            return false;
        }
        return id != null && id.equals(((PayementClient) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PayementClient{" +
            "id=" + getId() +
            ", payementClIdent=" + getPayementClIdent() +
            ", payementClDate='" + getPayementClDate() + "'" +
            ", payementClMode='" + getPayementClMode() + "'" +
            ", payementClEcheance='" + getPayementClEcheance() + "'" +
            ", payementClMontant=" + getPayementClMontant() +
            "}";
    }
}
