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
 * A Fournisseur.
 */
@Entity
@Table(name = "fournisseur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Fournisseur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fr_ident")
    private Integer frIdent;

    @Column(name = "fr_raison_social")
    private String frRaisonSocial;

    @Column(name = "fr_adresse")
    private String frAdresse;

    @Column(name = "fr_code_postal")
    private String frCodePostal;

    @Column(name = "fr_ville")
    private String frVille;

    @Column(name = "fr_country")
    private String frCountry;

    @Column(name = "fr_email")
    private String frEmail;

    @Column(name = "fr_numero_mobile")
    private String frNumeroMobile;

    @Column(name = "fr_numero_fax")
    private String frNumeroFax;

    @Column(name = "fr_numero_fixe")
    private String frNumeroFixe;

    @Column(name = "fr_date_creation")
    private LocalDate frDateCreation;

    @Column(name = "fr_date_update")
    private LocalDate frDateUpdate;

    @Column(name = "fr_status")
    private String frStatus;

    @Column(name = "fr_numero_siret")
    private String frNumeroSiret;

    @OneToMany(mappedBy = "fournisseur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ligneCmdFournisseurs", "fournisseur", "livraisonFr" }, allowSetters = true)
    private Set<CommandeFournisseur> commandeFournisseurs = new HashSet<>();

    @OneToMany(mappedBy = "fournisseur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fournisseur", "payementFr", "livraisonFr", "client", "payementCl" }, allowSetters = true)
    private Set<FactureAchat> factureAchats = new HashSet<>();

    @OneToMany(mappedBy = "fournisseur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fournisseur" }, allowSetters = true)
    private Set<ContactFournisseur> contactFournisseurs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "fournisseurs" }, allowSetters = true)
    private CiviliteFournisseur civilitefr;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Fournisseur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFrIdent() {
        return this.frIdent;
    }

    public Fournisseur frIdent(Integer frIdent) {
        this.setFrIdent(frIdent);
        return this;
    }

    public void setFrIdent(Integer frIdent) {
        this.frIdent = frIdent;
    }

    public String getFrRaisonSocial() {
        return this.frRaisonSocial;
    }

    public Fournisseur frRaisonSocial(String frRaisonSocial) {
        this.setFrRaisonSocial(frRaisonSocial);
        return this;
    }

    public void setFrRaisonSocial(String frRaisonSocial) {
        this.frRaisonSocial = frRaisonSocial;
    }

    public String getFrAdresse() {
        return this.frAdresse;
    }

    public Fournisseur frAdresse(String frAdresse) {
        this.setFrAdresse(frAdresse);
        return this;
    }

    public void setFrAdresse(String frAdresse) {
        this.frAdresse = frAdresse;
    }

    public String getFrCodePostal() {
        return this.frCodePostal;
    }

    public Fournisseur frCodePostal(String frCodePostal) {
        this.setFrCodePostal(frCodePostal);
        return this;
    }

    public void setFrCodePostal(String frCodePostal) {
        this.frCodePostal = frCodePostal;
    }

    public String getFrVille() {
        return this.frVille;
    }

    public Fournisseur frVille(String frVille) {
        this.setFrVille(frVille);
        return this;
    }

    public void setFrVille(String frVille) {
        this.frVille = frVille;
    }

    public String getFrCountry() {
        return this.frCountry;
    }

    public Fournisseur frCountry(String frCountry) {
        this.setFrCountry(frCountry);
        return this;
    }

    public void setFrCountry(String frCountry) {
        this.frCountry = frCountry;
    }

    public String getFrEmail() {
        return this.frEmail;
    }

    public Fournisseur frEmail(String frEmail) {
        this.setFrEmail(frEmail);
        return this;
    }

    public void setFrEmail(String frEmail) {
        this.frEmail = frEmail;
    }

    public String getFrNumeroMobile() {
        return this.frNumeroMobile;
    }

    public Fournisseur frNumeroMobile(String frNumeroMobile) {
        this.setFrNumeroMobile(frNumeroMobile);
        return this;
    }

    public void setFrNumeroMobile(String frNumeroMobile) {
        this.frNumeroMobile = frNumeroMobile;
    }

    public String getFrNumeroFax() {
        return this.frNumeroFax;
    }

    public Fournisseur frNumeroFax(String frNumeroFax) {
        this.setFrNumeroFax(frNumeroFax);
        return this;
    }

    public void setFrNumeroFax(String frNumeroFax) {
        this.frNumeroFax = frNumeroFax;
    }

    public String getFrNumeroFixe() {
        return this.frNumeroFixe;
    }

    public Fournisseur frNumeroFixe(String frNumeroFixe) {
        this.setFrNumeroFixe(frNumeroFixe);
        return this;
    }

    public void setFrNumeroFixe(String frNumeroFixe) {
        this.frNumeroFixe = frNumeroFixe;
    }

    public LocalDate getFrDateCreation() {
        return this.frDateCreation;
    }

    public Fournisseur frDateCreation(LocalDate frDateCreation) {
        this.setFrDateCreation(frDateCreation);
        return this;
    }

    public void setFrDateCreation(LocalDate frDateCreation) {
        this.frDateCreation = frDateCreation;
    }

    public LocalDate getFrDateUpdate() {
        return this.frDateUpdate;
    }

    public Fournisseur frDateUpdate(LocalDate frDateUpdate) {
        this.setFrDateUpdate(frDateUpdate);
        return this;
    }

    public void setFrDateUpdate(LocalDate frDateUpdate) {
        this.frDateUpdate = frDateUpdate;
    }

    public String getFrStatus() {
        return this.frStatus;
    }

    public Fournisseur frStatus(String frStatus) {
        this.setFrStatus(frStatus);
        return this;
    }

    public void setFrStatus(String frStatus) {
        this.frStatus = frStatus;
    }

    public String getFrNumeroSiret() {
        return this.frNumeroSiret;
    }

    public Fournisseur frNumeroSiret(String frNumeroSiret) {
        this.setFrNumeroSiret(frNumeroSiret);
        return this;
    }

    public void setFrNumeroSiret(String frNumeroSiret) {
        this.frNumeroSiret = frNumeroSiret;
    }

    public Set<CommandeFournisseur> getCommandeFournisseurs() {
        return this.commandeFournisseurs;
    }

    public void setCommandeFournisseurs(Set<CommandeFournisseur> commandeFournisseurs) {
        if (this.commandeFournisseurs != null) {
            this.commandeFournisseurs.forEach(i -> i.setFournisseur(null));
        }
        if (commandeFournisseurs != null) {
            commandeFournisseurs.forEach(i -> i.setFournisseur(this));
        }
        this.commandeFournisseurs = commandeFournisseurs;
    }

    public Fournisseur commandeFournisseurs(Set<CommandeFournisseur> commandeFournisseurs) {
        this.setCommandeFournisseurs(commandeFournisseurs);
        return this;
    }

    public Fournisseur addCommandeFournisseur(CommandeFournisseur commandeFournisseur) {
        this.commandeFournisseurs.add(commandeFournisseur);
        commandeFournisseur.setFournisseur(this);
        return this;
    }

    public Fournisseur removeCommandeFournisseur(CommandeFournisseur commandeFournisseur) {
        this.commandeFournisseurs.remove(commandeFournisseur);
        commandeFournisseur.setFournisseur(null);
        return this;
    }

    public Set<FactureAchat> getFactureAchats() {
        return this.factureAchats;
    }

    public void setFactureAchats(Set<FactureAchat> factureAchats) {
        if (this.factureAchats != null) {
            this.factureAchats.forEach(i -> i.setFournisseur(null));
        }
        if (factureAchats != null) {
            factureAchats.forEach(i -> i.setFournisseur(this));
        }
        this.factureAchats = factureAchats;
    }

    public Fournisseur factureAchats(Set<FactureAchat> factureAchats) {
        this.setFactureAchats(factureAchats);
        return this;
    }

    public Fournisseur addFactureAchat(FactureAchat factureAchat) {
        this.factureAchats.add(factureAchat);
        factureAchat.setFournisseur(this);
        return this;
    }

    public Fournisseur removeFactureAchat(FactureAchat factureAchat) {
        this.factureAchats.remove(factureAchat);
        factureAchat.setFournisseur(null);
        return this;
    }

    public Set<ContactFournisseur> getContactFournisseurs() {
        return this.contactFournisseurs;
    }

    public void setContactFournisseurs(Set<ContactFournisseur> contactFournisseurs) {
        if (this.contactFournisseurs != null) {
            this.contactFournisseurs.forEach(i -> i.setFournisseur(null));
        }
        if (contactFournisseurs != null) {
            contactFournisseurs.forEach(i -> i.setFournisseur(this));
        }
        this.contactFournisseurs = contactFournisseurs;
    }

    public Fournisseur contactFournisseurs(Set<ContactFournisseur> contactFournisseurs) {
        this.setContactFournisseurs(contactFournisseurs);
        return this;
    }

    public Fournisseur addContactFournisseur(ContactFournisseur contactFournisseur) {
        this.contactFournisseurs.add(contactFournisseur);
        contactFournisseur.setFournisseur(this);
        return this;
    }

    public Fournisseur removeContactFournisseur(ContactFournisseur contactFournisseur) {
        this.contactFournisseurs.remove(contactFournisseur);
        contactFournisseur.setFournisseur(null);
        return this;
    }

    public CiviliteFournisseur getCivilitefr() {
        return this.civilitefr;
    }

    public void setCivilitefr(CiviliteFournisseur civiliteFournisseur) {
        this.civilitefr = civiliteFournisseur;
    }

    public Fournisseur civilitefr(CiviliteFournisseur civiliteFournisseur) {
        this.setCivilitefr(civiliteFournisseur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fournisseur)) {
            return false;
        }
        return id != null && id.equals(((Fournisseur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fournisseur{" +
            "id=" + getId() +
            ", frIdent=" + getFrIdent() +
            ", frRaisonSocial='" + getFrRaisonSocial() + "'" +
            ", frAdresse='" + getFrAdresse() + "'" +
            ", frCodePostal='" + getFrCodePostal() + "'" +
            ", frVille='" + getFrVille() + "'" +
            ", frCountry='" + getFrCountry() + "'" +
            ", frEmail='" + getFrEmail() + "'" +
            ", frNumeroMobile='" + getFrNumeroMobile() + "'" +
            ", frNumeroFax='" + getFrNumeroFax() + "'" +
            ", frNumeroFixe='" + getFrNumeroFixe() + "'" +
            ", frDateCreation='" + getFrDateCreation() + "'" +
            ", frDateUpdate='" + getFrDateUpdate() + "'" +
            ", frStatus='" + getFrStatus() + "'" +
            ", frNumeroSiret='" + getFrNumeroSiret() + "'" +
            "}";
    }
}
