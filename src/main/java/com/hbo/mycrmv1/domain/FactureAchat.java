package com.hbo.mycrmv1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FactureAchat.
 */
@Entity
@Table(name = "facture_achat")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FactureAchat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "achat_ident_fac")
    private Integer achatIdentFac;

    @Column(name = "achat_date_effet")
    private LocalDate achatDateEffet;

    @Column(name = "achat_date_update")
    private LocalDate achatDateUpdate;

    @Column(name = "achat_status_fact")
    private String achatStatusFact;

    @Column(name = "achat_montant_ht")
    private Double achatMontantHT;

    @Column(name = "achat_montant_tva")
    private Double achatMontantTVA;

    @Column(name = "achat_montant_ttc")
    private Double achatMontantTTC;

    @ManyToOne
    @JsonIgnoreProperties(value = { "commandeFournisseurs", "factureAchats", "contactFournisseurs", "civilitefr" }, allowSetters = true)
    private Fournisseur fournisseur;

    @ManyToOne
    @JsonIgnoreProperties(value = { "factureAchats" }, allowSetters = true)
    private PayementFournisseur payementFr;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ligneLivFournisseurs", "commandeFournisseurs", "factureAchats" }, allowSetters = true)
    private LivraisonFr livraisonFr;

    @ManyToOne
    @JsonIgnoreProperties(value = { "commandeClients", "factureAchats", "contactClients", "civilitecl" }, allowSetters = true)
    private Client client;

    @ManyToOne
    @JsonIgnoreProperties(value = { "factureAchats" }, allowSetters = true)
    private PayementClient payementCl;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FactureAchat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAchatIdentFac() {
        return this.achatIdentFac;
    }

    public FactureAchat achatIdentFac(Integer achatIdentFac) {
        this.setAchatIdentFac(achatIdentFac);
        return this;
    }

    public void setAchatIdentFac(Integer achatIdentFac) {
        this.achatIdentFac = achatIdentFac;
    }

    public LocalDate getAchatDateEffet() {
        return this.achatDateEffet;
    }

    public FactureAchat achatDateEffet(LocalDate achatDateEffet) {
        this.setAchatDateEffet(achatDateEffet);
        return this;
    }

    public void setAchatDateEffet(LocalDate achatDateEffet) {
        this.achatDateEffet = achatDateEffet;
    }

    public LocalDate getAchatDateUpdate() {
        return this.achatDateUpdate;
    }

    public FactureAchat achatDateUpdate(LocalDate achatDateUpdate) {
        this.setAchatDateUpdate(achatDateUpdate);
        return this;
    }

    public void setAchatDateUpdate(LocalDate achatDateUpdate) {
        this.achatDateUpdate = achatDateUpdate;
    }

    public String getAchatStatusFact() {
        return this.achatStatusFact;
    }

    public FactureAchat achatStatusFact(String achatStatusFact) {
        this.setAchatStatusFact(achatStatusFact);
        return this;
    }

    public void setAchatStatusFact(String achatStatusFact) {
        this.achatStatusFact = achatStatusFact;
    }

    public Double getAchatMontantHT() {
        return this.achatMontantHT;
    }

    public FactureAchat achatMontantHT(Double achatMontantHT) {
        this.setAchatMontantHT(achatMontantHT);
        return this;
    }

    public void setAchatMontantHT(Double achatMontantHT) {
        this.achatMontantHT = achatMontantHT;
    }

    public Double getAchatMontantTVA() {
        return this.achatMontantTVA;
    }

    public FactureAchat achatMontantTVA(Double achatMontantTVA) {
        this.setAchatMontantTVA(achatMontantTVA);
        return this;
    }

    public void setAchatMontantTVA(Double achatMontantTVA) {
        this.achatMontantTVA = achatMontantTVA;
    }

    public Double getAchatMontantTTC() {
        return this.achatMontantTTC;
    }

    public FactureAchat achatMontantTTC(Double achatMontantTTC) {
        this.setAchatMontantTTC(achatMontantTTC);
        return this;
    }

    public void setAchatMontantTTC(Double achatMontantTTC) {
        this.achatMontantTTC = achatMontantTTC;
    }

    public Fournisseur getFournisseur() {
        return this.fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public FactureAchat fournisseur(Fournisseur fournisseur) {
        this.setFournisseur(fournisseur);
        return this;
    }

    public PayementFournisseur getPayementFr() {
        return this.payementFr;
    }

    public void setPayementFr(PayementFournisseur payementFournisseur) {
        this.payementFr = payementFournisseur;
    }

    public FactureAchat payementFr(PayementFournisseur payementFournisseur) {
        this.setPayementFr(payementFournisseur);
        return this;
    }

    public LivraisonFr getLivraisonFr() {
        return this.livraisonFr;
    }

    public void setLivraisonFr(LivraisonFr livraisonFr) {
        this.livraisonFr = livraisonFr;
    }

    public FactureAchat livraisonFr(LivraisonFr livraisonFr) {
        this.setLivraisonFr(livraisonFr);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public FactureAchat client(Client client) {
        this.setClient(client);
        return this;
    }

    public PayementClient getPayementCl() {
        return this.payementCl;
    }

    public void setPayementCl(PayementClient payementClient) {
        this.payementCl = payementClient;
    }

    public FactureAchat payementCl(PayementClient payementClient) {
        this.setPayementCl(payementClient);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FactureAchat)) {
            return false;
        }
        return id != null && id.equals(((FactureAchat) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FactureAchat{" +
            "id=" + getId() +
            ", achatIdentFac=" + getAchatIdentFac() +
            ", achatDateEffet='" + getAchatDateEffet() + "'" +
            ", achatDateUpdate='" + getAchatDateUpdate() + "'" +
            ", achatStatusFact='" + getAchatStatusFact() + "'" +
            ", achatMontantHT=" + getAchatMontantHT() +
            ", achatMontantTVA=" + getAchatMontantTVA() +
            ", achatMontantTTC=" + getAchatMontantTTC() +
            "}";
    }
}
