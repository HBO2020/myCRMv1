package com.hbo.mycrmv1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LigneCmdFournisseur.
 */
@Entity
@Table(name = "ligne_cmd_fournisseur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LigneCmdFournisseur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cmd_qn_fr")
    private Integer cmdQnFr;

    @Column(name = "cmd_nm_pieces")
    private Integer cmdNmPieces;

    @OneToMany(mappedBy = "ligneCmdFournisseur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "ligneCmdFournisseur", "ligneLivFournisseur", "uniteArticle", "ligneCmdClient", "ligneLivClient" },
        allowSetters = true
    )
    private Set<Article> articles = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "ligneCmdFournisseurs", "fournisseur", "livraisonFr" }, allowSetters = true)
    private CommandeFournisseur commandeFourniseur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LigneCmdFournisseur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCmdQnFr() {
        return this.cmdQnFr;
    }

    public LigneCmdFournisseur cmdQnFr(Integer cmdQnFr) {
        this.setCmdQnFr(cmdQnFr);
        return this;
    }

    public void setCmdQnFr(Integer cmdQnFr) {
        this.cmdQnFr = cmdQnFr;
    }

    public Integer getCmdNmPieces() {
        return this.cmdNmPieces;
    }

    public LigneCmdFournisseur cmdNmPieces(Integer cmdNmPieces) {
        this.setCmdNmPieces(cmdNmPieces);
        return this;
    }

    public void setCmdNmPieces(Integer cmdNmPieces) {
        this.cmdNmPieces = cmdNmPieces;
    }

    public Set<Article> getArticles() {
        return this.articles;
    }

    public void setArticles(Set<Article> articles) {
        if (this.articles != null) {
            this.articles.forEach(i -> i.setLigneCmdFournisseur(null));
        }
        if (articles != null) {
            articles.forEach(i -> i.setLigneCmdFournisseur(this));
        }
        this.articles = articles;
    }

    public LigneCmdFournisseur articles(Set<Article> articles) {
        this.setArticles(articles);
        return this;
    }

    public LigneCmdFournisseur addArticle(Article article) {
        this.articles.add(article);
        article.setLigneCmdFournisseur(this);
        return this;
    }

    public LigneCmdFournisseur removeArticle(Article article) {
        this.articles.remove(article);
        article.setLigneCmdFournisseur(null);
        return this;
    }

    public CommandeFournisseur getCommandeFourniseur() {
        return this.commandeFourniseur;
    }

    public void setCommandeFourniseur(CommandeFournisseur commandeFournisseur) {
        this.commandeFourniseur = commandeFournisseur;
    }

    public LigneCmdFournisseur commandeFourniseur(CommandeFournisseur commandeFournisseur) {
        this.setCommandeFourniseur(commandeFournisseur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LigneCmdFournisseur)) {
            return false;
        }
        return id != null && id.equals(((LigneCmdFournisseur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LigneCmdFournisseur{" +
            "id=" + getId() +
            ", cmdQnFr=" + getCmdQnFr() +
            ", cmdNmPieces=" + getCmdNmPieces() +
            "}";
    }
}
