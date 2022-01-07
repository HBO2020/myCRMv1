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
 * A CommandeFournisseur.
 */
@Entity
@Table(name = "commande_fournisseur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CommandeFournisseur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cmd_iden_fr")
    private Integer cmdIdenFr;

    @Column(name = "cmd_date_effet_fr")
    private LocalDate cmdDateEffetFr;

    @Column(name = "cmd_date_livraison_fr")
    private LocalDate cmdDateLivraisonFr;

    @OneToMany(mappedBy = "commandeFourniseur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "articles", "commandeFourniseur" }, allowSetters = true)
    private Set<LigneCmdFournisseur> ligneCmdFournisseurs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "commandeFournisseurs", "factureAchats", "contactFournisseurs", "civilitefr" }, allowSetters = true)
    private Fournisseur fournisseur;

    @ManyToOne
    @JsonIgnoreProperties(value = { "ligneLivFournisseurs", "commandeFournisseurs", "factureAchats" }, allowSetters = true)
    private LivraisonFr livraisonFr;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CommandeFournisseur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCmdIdenFr() {
        return this.cmdIdenFr;
    }

    public CommandeFournisseur cmdIdenFr(Integer cmdIdenFr) {
        this.setCmdIdenFr(cmdIdenFr);
        return this;
    }

    public void setCmdIdenFr(Integer cmdIdenFr) {
        this.cmdIdenFr = cmdIdenFr;
    }

    public LocalDate getCmdDateEffetFr() {
        return this.cmdDateEffetFr;
    }

    public CommandeFournisseur cmdDateEffetFr(LocalDate cmdDateEffetFr) {
        this.setCmdDateEffetFr(cmdDateEffetFr);
        return this;
    }

    public void setCmdDateEffetFr(LocalDate cmdDateEffetFr) {
        this.cmdDateEffetFr = cmdDateEffetFr;
    }

    public LocalDate getCmdDateLivraisonFr() {
        return this.cmdDateLivraisonFr;
    }

    public CommandeFournisseur cmdDateLivraisonFr(LocalDate cmdDateLivraisonFr) {
        this.setCmdDateLivraisonFr(cmdDateLivraisonFr);
        return this;
    }

    public void setCmdDateLivraisonFr(LocalDate cmdDateLivraisonFr) {
        this.cmdDateLivraisonFr = cmdDateLivraisonFr;
    }

    public Set<LigneCmdFournisseur> getLigneCmdFournisseurs() {
        return this.ligneCmdFournisseurs;
    }

    public void setLigneCmdFournisseurs(Set<LigneCmdFournisseur> ligneCmdFournisseurs) {
        if (this.ligneCmdFournisseurs != null) {
            this.ligneCmdFournisseurs.forEach(i -> i.setCommandeFourniseur(null));
        }
        if (ligneCmdFournisseurs != null) {
            ligneCmdFournisseurs.forEach(i -> i.setCommandeFourniseur(this));
        }
        this.ligneCmdFournisseurs = ligneCmdFournisseurs;
    }

    public CommandeFournisseur ligneCmdFournisseurs(Set<LigneCmdFournisseur> ligneCmdFournisseurs) {
        this.setLigneCmdFournisseurs(ligneCmdFournisseurs);
        return this;
    }

    public CommandeFournisseur addLigneCmdFournisseur(LigneCmdFournisseur ligneCmdFournisseur) {
        this.ligneCmdFournisseurs.add(ligneCmdFournisseur);
        ligneCmdFournisseur.setCommandeFourniseur(this);
        return this;
    }

    public CommandeFournisseur removeLigneCmdFournisseur(LigneCmdFournisseur ligneCmdFournisseur) {
        this.ligneCmdFournisseurs.remove(ligneCmdFournisseur);
        ligneCmdFournisseur.setCommandeFourniseur(null);
        return this;
    }

    public Fournisseur getFournisseur() {
        return this.fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public CommandeFournisseur fournisseur(Fournisseur fournisseur) {
        this.setFournisseur(fournisseur);
        return this;
    }

    public LivraisonFr getLivraisonFr() {
        return this.livraisonFr;
    }

    public void setLivraisonFr(LivraisonFr livraisonFr) {
        this.livraisonFr = livraisonFr;
    }

    public CommandeFournisseur livraisonFr(LivraisonFr livraisonFr) {
        this.setLivraisonFr(livraisonFr);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CommandeFournisseur)) {
            return false;
        }
        return id != null && id.equals(((CommandeFournisseur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CommandeFournisseur{" +
            "id=" + getId() +
            ", cmdIdenFr=" + getCmdIdenFr() +
            ", cmdDateEffetFr='" + getCmdDateEffetFr() + "'" +
            ", cmdDateLivraisonFr='" + getCmdDateLivraisonFr() + "'" +
            "}";
    }
}
