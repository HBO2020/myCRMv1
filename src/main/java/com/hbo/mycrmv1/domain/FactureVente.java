package com.hbo.mycrmv1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FactureVente.
 */
@Entity
@Table(name = "facture_vente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FactureVente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "vente_ident_fac")
    private Integer venteIdentFac;

    @Column(name = "vente_date_effet")
    private LocalDate venteDateEffet;

    @Column(name = "vente_date_update")
    private LocalDate venteDateUpdate;

    @Column(name = "vente_status_fact")
    private String venteStatusFact;

    @Column(name = "vente_montant_ht")
    private Double venteMontantHT;

    @Column(name = "vente_montant_tva")
    private Double venteMontantTVA;

    @Column(name = "vente_montant_ttc")
    private Double venteMontantTTC;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ligneLivClients", "commandeClients", "factureVentes" }, allowSetters = true)
    private LivraisonCl livraisonCl;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FactureVente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVenteIdentFac() {
        return this.venteIdentFac;
    }

    public FactureVente venteIdentFac(Integer venteIdentFac) {
        this.setVenteIdentFac(venteIdentFac);
        return this;
    }

    public void setVenteIdentFac(Integer venteIdentFac) {
        this.venteIdentFac = venteIdentFac;
    }

    public LocalDate getVenteDateEffet() {
        return this.venteDateEffet;
    }

    public FactureVente venteDateEffet(LocalDate venteDateEffet) {
        this.setVenteDateEffet(venteDateEffet);
        return this;
    }

    public void setVenteDateEffet(LocalDate venteDateEffet) {
        this.venteDateEffet = venteDateEffet;
    }

    public LocalDate getVenteDateUpdate() {
        return this.venteDateUpdate;
    }

    public FactureVente venteDateUpdate(LocalDate venteDateUpdate) {
        this.setVenteDateUpdate(venteDateUpdate);
        return this;
    }

    public void setVenteDateUpdate(LocalDate venteDateUpdate) {
        this.venteDateUpdate = venteDateUpdate;
    }

    public String getVenteStatusFact() {
        return this.venteStatusFact;
    }

    public FactureVente venteStatusFact(String venteStatusFact) {
        this.setVenteStatusFact(venteStatusFact);
        return this;
    }

    public void setVenteStatusFact(String venteStatusFact) {
        this.venteStatusFact = venteStatusFact;
    }

    public Double getVenteMontantHT() {
        return this.venteMontantHT;
    }

    public FactureVente venteMontantHT(Double venteMontantHT) {
        this.setVenteMontantHT(venteMontantHT);
        return this;
    }

    public void setVenteMontantHT(Double venteMontantHT) {
        this.venteMontantHT = venteMontantHT;
    }

    public Double getVenteMontantTVA() {
        return this.venteMontantTVA;
    }

    public FactureVente venteMontantTVA(Double venteMontantTVA) {
        this.setVenteMontantTVA(venteMontantTVA);
        return this;
    }

    public void setVenteMontantTVA(Double venteMontantTVA) {
        this.venteMontantTVA = venteMontantTVA;
    }

    public Double getVenteMontantTTC() {
        return this.venteMontantTTC;
    }

    public FactureVente venteMontantTTC(Double venteMontantTTC) {
        this.setVenteMontantTTC(venteMontantTTC);
        return this;
    }

    public void setVenteMontantTTC(Double venteMontantTTC) {
        this.venteMontantTTC = venteMontantTTC;
    }

    public LivraisonCl getLivraisonCl() {
        return this.livraisonCl;
    }

    public void setLivraisonCl(LivraisonCl livraisonCl) {
        this.livraisonCl = livraisonCl;
    }

    public FactureVente livraisonCl(LivraisonCl livraisonCl) {
        this.setLivraisonCl(livraisonCl);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FactureVente)) {
            return false;
        }
        return id != null && id.equals(((FactureVente) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FactureVente{" +
            "id=" + getId() +
            ", venteIdentFac=" + getVenteIdentFac() +
            ", venteDateEffet='" + getVenteDateEffet() + "'" +
            ", venteDateUpdate='" + getVenteDateUpdate() + "'" +
            ", venteStatusFact='" + getVenteStatusFact() + "'" +
            ", venteMontantHT=" + getVenteMontantHT() +
            ", venteMontantTVA=" + getVenteMontantTVA() +
            ", venteMontantTTC=" + getVenteMontantTTC() +
            "}";
    }
}
