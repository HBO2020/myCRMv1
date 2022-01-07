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
 * A LivraisonFr.
 */
@Entity
@Table(name = "livraison_fr")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LivraisonFr implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "bon_liv_ident")
    private Integer bonLivIdent;

    @Column(name = "liv_fr_date")
    private LocalDate livFrDate;

    @Column(name = "liv_fr_date_update")
    private LocalDate livFrDateUpdate;

    @Column(name = "liv_date_effet")
    private LocalDate livDateEffet;

    @Column(name = "bon_liv_total")
    private Double bonLivTotal;

    @OneToMany(mappedBy = "livraisonFr")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "articles", "livraisonFr" }, allowSetters = true)
    private Set<LigneLivFournisseur> ligneLivFournisseurs = new HashSet<>();

    @OneToMany(mappedBy = "livraisonFr")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ligneCmdFournisseurs", "fournisseur", "livraisonFr" }, allowSetters = true)
    private Set<CommandeFournisseur> commandeFournisseurs = new HashSet<>();

    @OneToMany(mappedBy = "livraisonFr")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fournisseur", "payementFr", "livraisonFr", "client", "payementCl" }, allowSetters = true)
    private Set<FactureAchat> factureAchats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LivraisonFr id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBonLivIdent() {
        return this.bonLivIdent;
    }

    public LivraisonFr bonLivIdent(Integer bonLivIdent) {
        this.setBonLivIdent(bonLivIdent);
        return this;
    }

    public void setBonLivIdent(Integer bonLivIdent) {
        this.bonLivIdent = bonLivIdent;
    }

    public LocalDate getLivFrDate() {
        return this.livFrDate;
    }

    public LivraisonFr livFrDate(LocalDate livFrDate) {
        this.setLivFrDate(livFrDate);
        return this;
    }

    public void setLivFrDate(LocalDate livFrDate) {
        this.livFrDate = livFrDate;
    }

    public LocalDate getLivFrDateUpdate() {
        return this.livFrDateUpdate;
    }

    public LivraisonFr livFrDateUpdate(LocalDate livFrDateUpdate) {
        this.setLivFrDateUpdate(livFrDateUpdate);
        return this;
    }

    public void setLivFrDateUpdate(LocalDate livFrDateUpdate) {
        this.livFrDateUpdate = livFrDateUpdate;
    }

    public LocalDate getLivDateEffet() {
        return this.livDateEffet;
    }

    public LivraisonFr livDateEffet(LocalDate livDateEffet) {
        this.setLivDateEffet(livDateEffet);
        return this;
    }

    public void setLivDateEffet(LocalDate livDateEffet) {
        this.livDateEffet = livDateEffet;
    }

    public Double getBonLivTotal() {
        return this.bonLivTotal;
    }

    public LivraisonFr bonLivTotal(Double bonLivTotal) {
        this.setBonLivTotal(bonLivTotal);
        return this;
    }

    public void setBonLivTotal(Double bonLivTotal) {
        this.bonLivTotal = bonLivTotal;
    }

    public Set<LigneLivFournisseur> getLigneLivFournisseurs() {
        return this.ligneLivFournisseurs;
    }

    public void setLigneLivFournisseurs(Set<LigneLivFournisseur> ligneLivFournisseurs) {
        if (this.ligneLivFournisseurs != null) {
            this.ligneLivFournisseurs.forEach(i -> i.setLivraisonFr(null));
        }
        if (ligneLivFournisseurs != null) {
            ligneLivFournisseurs.forEach(i -> i.setLivraisonFr(this));
        }
        this.ligneLivFournisseurs = ligneLivFournisseurs;
    }

    public LivraisonFr ligneLivFournisseurs(Set<LigneLivFournisseur> ligneLivFournisseurs) {
        this.setLigneLivFournisseurs(ligneLivFournisseurs);
        return this;
    }

    public LivraisonFr addLigneLivFournisseur(LigneLivFournisseur ligneLivFournisseur) {
        this.ligneLivFournisseurs.add(ligneLivFournisseur);
        ligneLivFournisseur.setLivraisonFr(this);
        return this;
    }

    public LivraisonFr removeLigneLivFournisseur(LigneLivFournisseur ligneLivFournisseur) {
        this.ligneLivFournisseurs.remove(ligneLivFournisseur);
        ligneLivFournisseur.setLivraisonFr(null);
        return this;
    }

    public Set<CommandeFournisseur> getCommandeFournisseurs() {
        return this.commandeFournisseurs;
    }

    public void setCommandeFournisseurs(Set<CommandeFournisseur> commandeFournisseurs) {
        if (this.commandeFournisseurs != null) {
            this.commandeFournisseurs.forEach(i -> i.setLivraisonFr(null));
        }
        if (commandeFournisseurs != null) {
            commandeFournisseurs.forEach(i -> i.setLivraisonFr(this));
        }
        this.commandeFournisseurs = commandeFournisseurs;
    }

    public LivraisonFr commandeFournisseurs(Set<CommandeFournisseur> commandeFournisseurs) {
        this.setCommandeFournisseurs(commandeFournisseurs);
        return this;
    }

    public LivraisonFr addCommandeFournisseur(CommandeFournisseur commandeFournisseur) {
        this.commandeFournisseurs.add(commandeFournisseur);
        commandeFournisseur.setLivraisonFr(this);
        return this;
    }

    public LivraisonFr removeCommandeFournisseur(CommandeFournisseur commandeFournisseur) {
        this.commandeFournisseurs.remove(commandeFournisseur);
        commandeFournisseur.setLivraisonFr(null);
        return this;
    }

    public Set<FactureAchat> getFactureAchats() {
        return this.factureAchats;
    }

    public void setFactureAchats(Set<FactureAchat> factureAchats) {
        if (this.factureAchats != null) {
            this.factureAchats.forEach(i -> i.setLivraisonFr(null));
        }
        if (factureAchats != null) {
            factureAchats.forEach(i -> i.setLivraisonFr(this));
        }
        this.factureAchats = factureAchats;
    }

    public LivraisonFr factureAchats(Set<FactureAchat> factureAchats) {
        this.setFactureAchats(factureAchats);
        return this;
    }

    public LivraisonFr addFactureAchat(FactureAchat factureAchat) {
        this.factureAchats.add(factureAchat);
        factureAchat.setLivraisonFr(this);
        return this;
    }

    public LivraisonFr removeFactureAchat(FactureAchat factureAchat) {
        this.factureAchats.remove(factureAchat);
        factureAchat.setLivraisonFr(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LivraisonFr)) {
            return false;
        }
        return id != null && id.equals(((LivraisonFr) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LivraisonFr{" +
            "id=" + getId() +
            ", bonLivIdent=" + getBonLivIdent() +
            ", livFrDate='" + getLivFrDate() + "'" +
            ", livFrDateUpdate='" + getLivFrDateUpdate() + "'" +
            ", livDateEffet='" + getLivDateEffet() + "'" +
            ", bonLivTotal=" + getBonLivTotal() +
            "}";
    }
}
