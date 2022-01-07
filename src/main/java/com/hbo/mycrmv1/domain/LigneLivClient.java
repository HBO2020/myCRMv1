package com.hbo.mycrmv1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LigneLivClient.
 */
@Entity
@Table(name = "ligne_liv_client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LigneLivClient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "liv_quantite_cl")
    private Integer livQuantiteCl;

    @Column(name = "liv_nm_pieces_cl")
    private Integer livNmPiecesCl;

    @Column(name = "liv_total_prix_cl")
    private Double livTotalPrixCl;

    @OneToMany(mappedBy = "ligneLivClient")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "ligneCmdFournisseur", "ligneLivFournisseur", "uniteArticle", "ligneCmdClient", "ligneLivClient" },
        allowSetters = true
    )
    private Set<Article> articles = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "ligneLivClients", "commandeClients", "factureVentes" }, allowSetters = true)
    private LivraisonCl livraisonCl;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LigneLivClient id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLivQuantiteCl() {
        return this.livQuantiteCl;
    }

    public LigneLivClient livQuantiteCl(Integer livQuantiteCl) {
        this.setLivQuantiteCl(livQuantiteCl);
        return this;
    }

    public void setLivQuantiteCl(Integer livQuantiteCl) {
        this.livQuantiteCl = livQuantiteCl;
    }

    public Integer getLivNmPiecesCl() {
        return this.livNmPiecesCl;
    }

    public LigneLivClient livNmPiecesCl(Integer livNmPiecesCl) {
        this.setLivNmPiecesCl(livNmPiecesCl);
        return this;
    }

    public void setLivNmPiecesCl(Integer livNmPiecesCl) {
        this.livNmPiecesCl = livNmPiecesCl;
    }

    public Double getLivTotalPrixCl() {
        return this.livTotalPrixCl;
    }

    public LigneLivClient livTotalPrixCl(Double livTotalPrixCl) {
        this.setLivTotalPrixCl(livTotalPrixCl);
        return this;
    }

    public void setLivTotalPrixCl(Double livTotalPrixCl) {
        this.livTotalPrixCl = livTotalPrixCl;
    }

    public Set<Article> getArticles() {
        return this.articles;
    }

    public void setArticles(Set<Article> articles) {
        if (this.articles != null) {
            this.articles.forEach(i -> i.setLigneLivClient(null));
        }
        if (articles != null) {
            articles.forEach(i -> i.setLigneLivClient(this));
        }
        this.articles = articles;
    }

    public LigneLivClient articles(Set<Article> articles) {
        this.setArticles(articles);
        return this;
    }

    public LigneLivClient addArticle(Article article) {
        this.articles.add(article);
        article.setLigneLivClient(this);
        return this;
    }

    public LigneLivClient removeArticle(Article article) {
        this.articles.remove(article);
        article.setLigneLivClient(null);
        return this;
    }

    public LivraisonCl getLivraisonCl() {
        return this.livraisonCl;
    }

    public void setLivraisonCl(LivraisonCl livraisonCl) {
        this.livraisonCl = livraisonCl;
    }

    public LigneLivClient livraisonCl(LivraisonCl livraisonCl) {
        this.setLivraisonCl(livraisonCl);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LigneLivClient)) {
            return false;
        }
        return id != null && id.equals(((LigneLivClient) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LigneLivClient{" +
            "id=" + getId() +
            ", livQuantiteCl=" + getLivQuantiteCl() +
            ", livNmPiecesCl=" + getLivNmPiecesCl() +
            ", livTotalPrixCl=" + getLivTotalPrixCl() +
            "}";
    }
}
