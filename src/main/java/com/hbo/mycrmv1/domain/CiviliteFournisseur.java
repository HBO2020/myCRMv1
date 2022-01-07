package com.hbo.mycrmv1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CiviliteFournisseur.
 */
@Entity
@Table(name = "civilite_fournisseur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CiviliteFournisseur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "civilite_fr_libelle")
    private String civiliteFrLibelle;

    @Column(name = "civilite_fr_code")
    private Integer civiliteFrCode;

    @OneToMany(mappedBy = "civilitefr")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "commandeFournisseurs", "factureAchats", "contactFournisseurs", "civilitefr" }, allowSetters = true)
    private Set<Fournisseur> fournisseurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CiviliteFournisseur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCiviliteFrLibelle() {
        return this.civiliteFrLibelle;
    }

    public CiviliteFournisseur civiliteFrLibelle(String civiliteFrLibelle) {
        this.setCiviliteFrLibelle(civiliteFrLibelle);
        return this;
    }

    public void setCiviliteFrLibelle(String civiliteFrLibelle) {
        this.civiliteFrLibelle = civiliteFrLibelle;
    }

    public Integer getCiviliteFrCode() {
        return this.civiliteFrCode;
    }

    public CiviliteFournisseur civiliteFrCode(Integer civiliteFrCode) {
        this.setCiviliteFrCode(civiliteFrCode);
        return this;
    }

    public void setCiviliteFrCode(Integer civiliteFrCode) {
        this.civiliteFrCode = civiliteFrCode;
    }

    public Set<Fournisseur> getFournisseurs() {
        return this.fournisseurs;
    }

    public void setFournisseurs(Set<Fournisseur> fournisseurs) {
        if (this.fournisseurs != null) {
            this.fournisseurs.forEach(i -> i.setCivilitefr(null));
        }
        if (fournisseurs != null) {
            fournisseurs.forEach(i -> i.setCivilitefr(this));
        }
        this.fournisseurs = fournisseurs;
    }

    public CiviliteFournisseur fournisseurs(Set<Fournisseur> fournisseurs) {
        this.setFournisseurs(fournisseurs);
        return this;
    }

    public CiviliteFournisseur addFournisseur(Fournisseur fournisseur) {
        this.fournisseurs.add(fournisseur);
        fournisseur.setCivilitefr(this);
        return this;
    }

    public CiviliteFournisseur removeFournisseur(Fournisseur fournisseur) {
        this.fournisseurs.remove(fournisseur);
        fournisseur.setCivilitefr(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CiviliteFournisseur)) {
            return false;
        }
        return id != null && id.equals(((CiviliteFournisseur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CiviliteFournisseur{" +
            "id=" + getId() +
            ", civiliteFrLibelle='" + getCiviliteFrLibelle() + "'" +
            ", civiliteFrCode=" + getCiviliteFrCode() +
            "}";
    }
}
