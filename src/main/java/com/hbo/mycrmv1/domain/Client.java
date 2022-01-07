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
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cl_ident")
    private Integer clIdent;

    @Column(name = "cl_raison_social")
    private String clRaisonSocial;

    @Column(name = "cl_adresse")
    private String clAdresse;

    @Column(name = "cl_code_postal")
    private String clCodePostal;

    @Column(name = "cl_ville")
    private String clVille;

    @Column(name = "cl_country")
    private String clCountry;

    @Column(name = "cl_email")
    private String clEmail;

    @Column(name = "cl_numero_mobile")
    private String clNumeroMobile;

    @Column(name = "cl_numero_fax")
    private String clNumeroFax;

    @Column(name = "cl_numero_fixe")
    private String clNumeroFixe;

    @Column(name = "cl_date_creation")
    private LocalDate clDateCreation;

    @Column(name = "cl_date_update")
    private LocalDate clDateUpdate;

    @Column(name = "cl_status")
    private String clStatus;

    @Column(name = "cl_numero_siret")
    private String clNumeroSiret;

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "ligneCmdClients", "client", "livraisonCl" }, allowSetters = true)
    private Set<CommandeClient> commandeClients = new HashSet<>();

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fournisseur", "payementFr", "livraisonFr", "client", "payementCl" }, allowSetters = true)
    private Set<FactureAchat> factureAchats = new HashSet<>();

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "client" }, allowSetters = true)
    private Set<ContactClient> contactClients = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "clients" }, allowSetters = true)
    private CiviliteClient civilitecl;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Client id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getClIdent() {
        return this.clIdent;
    }

    public Client clIdent(Integer clIdent) {
        this.setClIdent(clIdent);
        return this;
    }

    public void setClIdent(Integer clIdent) {
        this.clIdent = clIdent;
    }

    public String getClRaisonSocial() {
        return this.clRaisonSocial;
    }

    public Client clRaisonSocial(String clRaisonSocial) {
        this.setClRaisonSocial(clRaisonSocial);
        return this;
    }

    public void setClRaisonSocial(String clRaisonSocial) {
        this.clRaisonSocial = clRaisonSocial;
    }

    public String getClAdresse() {
        return this.clAdresse;
    }

    public Client clAdresse(String clAdresse) {
        this.setClAdresse(clAdresse);
        return this;
    }

    public void setClAdresse(String clAdresse) {
        this.clAdresse = clAdresse;
    }

    public String getClCodePostal() {
        return this.clCodePostal;
    }

    public Client clCodePostal(String clCodePostal) {
        this.setClCodePostal(clCodePostal);
        return this;
    }

    public void setClCodePostal(String clCodePostal) {
        this.clCodePostal = clCodePostal;
    }

    public String getClVille() {
        return this.clVille;
    }

    public Client clVille(String clVille) {
        this.setClVille(clVille);
        return this;
    }

    public void setClVille(String clVille) {
        this.clVille = clVille;
    }

    public String getClCountry() {
        return this.clCountry;
    }

    public Client clCountry(String clCountry) {
        this.setClCountry(clCountry);
        return this;
    }

    public void setClCountry(String clCountry) {
        this.clCountry = clCountry;
    }

    public String getClEmail() {
        return this.clEmail;
    }

    public Client clEmail(String clEmail) {
        this.setClEmail(clEmail);
        return this;
    }

    public void setClEmail(String clEmail) {
        this.clEmail = clEmail;
    }

    public String getClNumeroMobile() {
        return this.clNumeroMobile;
    }

    public Client clNumeroMobile(String clNumeroMobile) {
        this.setClNumeroMobile(clNumeroMobile);
        return this;
    }

    public void setClNumeroMobile(String clNumeroMobile) {
        this.clNumeroMobile = clNumeroMobile;
    }

    public String getClNumeroFax() {
        return this.clNumeroFax;
    }

    public Client clNumeroFax(String clNumeroFax) {
        this.setClNumeroFax(clNumeroFax);
        return this;
    }

    public void setClNumeroFax(String clNumeroFax) {
        this.clNumeroFax = clNumeroFax;
    }

    public String getClNumeroFixe() {
        return this.clNumeroFixe;
    }

    public Client clNumeroFixe(String clNumeroFixe) {
        this.setClNumeroFixe(clNumeroFixe);
        return this;
    }

    public void setClNumeroFixe(String clNumeroFixe) {
        this.clNumeroFixe = clNumeroFixe;
    }

    public LocalDate getClDateCreation() {
        return this.clDateCreation;
    }

    public Client clDateCreation(LocalDate clDateCreation) {
        this.setClDateCreation(clDateCreation);
        return this;
    }

    public void setClDateCreation(LocalDate clDateCreation) {
        this.clDateCreation = clDateCreation;
    }

    public LocalDate getClDateUpdate() {
        return this.clDateUpdate;
    }

    public Client clDateUpdate(LocalDate clDateUpdate) {
        this.setClDateUpdate(clDateUpdate);
        return this;
    }

    public void setClDateUpdate(LocalDate clDateUpdate) {
        this.clDateUpdate = clDateUpdate;
    }

    public String getClStatus() {
        return this.clStatus;
    }

    public Client clStatus(String clStatus) {
        this.setClStatus(clStatus);
        return this;
    }

    public void setClStatus(String clStatus) {
        this.clStatus = clStatus;
    }

    public String getClNumeroSiret() {
        return this.clNumeroSiret;
    }

    public Client clNumeroSiret(String clNumeroSiret) {
        this.setClNumeroSiret(clNumeroSiret);
        return this;
    }

    public void setClNumeroSiret(String clNumeroSiret) {
        this.clNumeroSiret = clNumeroSiret;
    }

    public Set<CommandeClient> getCommandeClients() {
        return this.commandeClients;
    }

    public void setCommandeClients(Set<CommandeClient> commandeClients) {
        if (this.commandeClients != null) {
            this.commandeClients.forEach(i -> i.setClient(null));
        }
        if (commandeClients != null) {
            commandeClients.forEach(i -> i.setClient(this));
        }
        this.commandeClients = commandeClients;
    }

    public Client commandeClients(Set<CommandeClient> commandeClients) {
        this.setCommandeClients(commandeClients);
        return this;
    }

    public Client addCommandeClient(CommandeClient commandeClient) {
        this.commandeClients.add(commandeClient);
        commandeClient.setClient(this);
        return this;
    }

    public Client removeCommandeClient(CommandeClient commandeClient) {
        this.commandeClients.remove(commandeClient);
        commandeClient.setClient(null);
        return this;
    }

    public Set<FactureAchat> getFactureAchats() {
        return this.factureAchats;
    }

    public void setFactureAchats(Set<FactureAchat> factureAchats) {
        if (this.factureAchats != null) {
            this.factureAchats.forEach(i -> i.setClient(null));
        }
        if (factureAchats != null) {
            factureAchats.forEach(i -> i.setClient(this));
        }
        this.factureAchats = factureAchats;
    }

    public Client factureAchats(Set<FactureAchat> factureAchats) {
        this.setFactureAchats(factureAchats);
        return this;
    }

    public Client addFactureAchat(FactureAchat factureAchat) {
        this.factureAchats.add(factureAchat);
        factureAchat.setClient(this);
        return this;
    }

    public Client removeFactureAchat(FactureAchat factureAchat) {
        this.factureAchats.remove(factureAchat);
        factureAchat.setClient(null);
        return this;
    }

    public Set<ContactClient> getContactClients() {
        return this.contactClients;
    }

    public void setContactClients(Set<ContactClient> contactClients) {
        if (this.contactClients != null) {
            this.contactClients.forEach(i -> i.setClient(null));
        }
        if (contactClients != null) {
            contactClients.forEach(i -> i.setClient(this));
        }
        this.contactClients = contactClients;
    }

    public Client contactClients(Set<ContactClient> contactClients) {
        this.setContactClients(contactClients);
        return this;
    }

    public Client addContactClient(ContactClient contactClient) {
        this.contactClients.add(contactClient);
        contactClient.setClient(this);
        return this;
    }

    public Client removeContactClient(ContactClient contactClient) {
        this.contactClients.remove(contactClient);
        contactClient.setClient(null);
        return this;
    }

    public CiviliteClient getCivilitecl() {
        return this.civilitecl;
    }

    public void setCivilitecl(CiviliteClient civiliteClient) {
        this.civilitecl = civiliteClient;
    }

    public Client civilitecl(CiviliteClient civiliteClient) {
        this.setCivilitecl(civiliteClient);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", clIdent=" + getClIdent() +
            ", clRaisonSocial='" + getClRaisonSocial() + "'" +
            ", clAdresse='" + getClAdresse() + "'" +
            ", clCodePostal='" + getClCodePostal() + "'" +
            ", clVille='" + getClVille() + "'" +
            ", clCountry='" + getClCountry() + "'" +
            ", clEmail='" + getClEmail() + "'" +
            ", clNumeroMobile='" + getClNumeroMobile() + "'" +
            ", clNumeroFax='" + getClNumeroFax() + "'" +
            ", clNumeroFixe='" + getClNumeroFixe() + "'" +
            ", clDateCreation='" + getClDateCreation() + "'" +
            ", clDateUpdate='" + getClDateUpdate() + "'" +
            ", clStatus='" + getClStatus() + "'" +
            ", clNumeroSiret='" + getClNumeroSiret() + "'" +
            "}";
    }
}
