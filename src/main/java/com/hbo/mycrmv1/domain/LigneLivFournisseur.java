package com.hbo.mycrmv1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LigneLivFournisseur.
 */
@Entity
@Table(name = "ligne_liv_fournisseur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LigneLivFournisseur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "liv_fr_quantite")
    private Integer livFrQuantite;

    @Column(name = "liv_fr_nm_pieces")
    private Integer livFrNmPieces;

    @Column(name = "liv_fr_total_prix")
    private Double livFrTotalPrix;

    @OneToMany(mappedBy = "ligneLivFournisseur")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "ligneCmdFournisseur", "ligneLivFournisseur", "uniteArticle", "ligneCmdClient", "ligneLivClient" },
        allowSetters = true
    )
    private Set<Article> articles = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "ligneLivFournisseurs", "commandeFournisseurs", "factureAchats" }, allowSetters = true)
    private LivraisonFr livraisonFr;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LigneLivFournisseur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLivFrQuantite() {
        return this.livFrQuantite;
    }

    public LigneLivFournisseur livFrQuantite(Integer livFrQuantite) {
        this.setLivFrQuantite(livFrQuantite);
        return this;
    }

    public void setLivFrQuantite(Integer livFrQuantite) {
        this.livFrQuantite = livFrQuantite;
    }

    public Integer getLivFrNmPieces() {
        return this.livFrNmPieces;
    }

    public LigneLivFournisseur livFrNmPieces(Integer livFrNmPieces) {
        this.setLivFrNmPieces(livFrNmPieces);
        return this;
    }

    public void setLivFrNmPieces(Integer livFrNmPieces) {
        this.livFrNmPieces = livFrNmPieces;
    }

    public Double getLivFrTotalPrix() {
        return this.livFrTotalPrix;
    }

    public LigneLivFournisseur livFrTotalPrix(Double livFrTotalPrix) {
        this.setLivFrTotalPrix(livFrTotalPrix);
        return this;
    }

    public void setLivFrTotalPrix(Double livFrTotalPrix) {
        this.livFrTotalPrix = livFrTotalPrix;
    }

    public Set<Article> getArticles() {
        return this.articles;
    }

    public void setArticles(Set<Article> articles) {
        if (this.articles != null) {
            this.articles.forEach(i -> i.setLigneLivFournisseur(null));
        }
        if (articles != null) {
            articles.forEach(i -> i.setLigneLivFournisseur(this));
        }
        this.articles = articles;
    }

    public LigneLivFournisseur articles(Set<Article> articles) {
        this.setArticles(articles);
        return this;
    }

    public LigneLivFournisseur addArticle(Article article) {
        this.articles.add(article);
        article.setLigneLivFournisseur(this);
        return this;
    }

    public LigneLivFournisseur removeArticle(Article article) {
        this.articles.remove(article);
        article.setLigneLivFournisseur(null);
        return this;
    }

    public LivraisonFr getLivraisonFr() {
        return this.livraisonFr;
    }

    public void setLivraisonFr(LivraisonFr livraisonFr) {
        this.livraisonFr = livraisonFr;
    }

    public LigneLivFournisseur livraisonFr(LivraisonFr livraisonFr) {
        this.setLivraisonFr(livraisonFr);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LigneLivFournisseur)) {
            return false;
        }
        return id != null && id.equals(((LigneLivFournisseur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LigneLivFournisseur{" +
            "id=" + getId() +
            ", livFrQuantite=" + getLivFrQuantite() +
            ", livFrNmPieces=" + getLivFrNmPieces() +
            ", livFrTotalPrix=" + getLivFrTotalPrix() +
            "}";
    }
}
