package com.hbo.mycrmv1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UniteArticle.
 */
@Entity
@Table(name = "unite_article")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UniteArticle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "unite_code")
    private Integer uniteCode;

    @Column(name = "unite_libelle")
    private String uniteLibelle;

    @Column(name = "unite_option")
    private String uniteOption;

    @OneToMany(mappedBy = "uniteArticle")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "ligneCmdFournisseur", "ligneLivFournisseur", "uniteArticle", "ligneCmdClient", "ligneLivClient" },
        allowSetters = true
    )
    private Set<Article> articles = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UniteArticle id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUniteCode() {
        return this.uniteCode;
    }

    public UniteArticle uniteCode(Integer uniteCode) {
        this.setUniteCode(uniteCode);
        return this;
    }

    public void setUniteCode(Integer uniteCode) {
        this.uniteCode = uniteCode;
    }

    public String getUniteLibelle() {
        return this.uniteLibelle;
    }

    public UniteArticle uniteLibelle(String uniteLibelle) {
        this.setUniteLibelle(uniteLibelle);
        return this;
    }

    public void setUniteLibelle(String uniteLibelle) {
        this.uniteLibelle = uniteLibelle;
    }

    public String getUniteOption() {
        return this.uniteOption;
    }

    public UniteArticle uniteOption(String uniteOption) {
        this.setUniteOption(uniteOption);
        return this;
    }

    public void setUniteOption(String uniteOption) {
        this.uniteOption = uniteOption;
    }

    public Set<Article> getArticles() {
        return this.articles;
    }

    public void setArticles(Set<Article> articles) {
        if (this.articles != null) {
            this.articles.forEach(i -> i.setUniteArticle(null));
        }
        if (articles != null) {
            articles.forEach(i -> i.setUniteArticle(this));
        }
        this.articles = articles;
    }

    public UniteArticle articles(Set<Article> articles) {
        this.setArticles(articles);
        return this;
    }

    public UniteArticle addArticle(Article article) {
        this.articles.add(article);
        article.setUniteArticle(this);
        return this;
    }

    public UniteArticle removeArticle(Article article) {
        this.articles.remove(article);
        article.setUniteArticle(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UniteArticle)) {
            return false;
        }
        return id != null && id.equals(((UniteArticle) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UniteArticle{" +
            "id=" + getId() +
            ", uniteCode=" + getUniteCode() +
            ", uniteLibelle='" + getUniteLibelle() + "'" +
            ", uniteOption='" + getUniteOption() + "'" +
            "}";
    }
}
