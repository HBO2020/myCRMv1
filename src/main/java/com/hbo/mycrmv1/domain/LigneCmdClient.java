package com.hbo.mycrmv1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LigneCmdClient.
 */
@Entity
@Table(name = "ligne_cmd_client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LigneCmdClient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "cmd_qn_cl")
    private Integer cmdQnCl;

    @Column(name = "cmd_nm_pieces_cl")
    private Integer cmdNmPiecesCl;

    @OneToMany(mappedBy = "ligneCmdClient")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "ligneCmdFournisseur", "ligneLivFournisseur", "uniteArticle", "ligneCmdClient", "ligneLivClient" },
        allowSetters = true
    )
    private Set<Article> articles = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "ligneCmdClients", "client", "livraisonCl" }, allowSetters = true)
    private CommandeClient commandeClient;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LigneCmdClient id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCmdQnCl() {
        return this.cmdQnCl;
    }

    public LigneCmdClient cmdQnCl(Integer cmdQnCl) {
        this.setCmdQnCl(cmdQnCl);
        return this;
    }

    public void setCmdQnCl(Integer cmdQnCl) {
        this.cmdQnCl = cmdQnCl;
    }

    public Integer getCmdNmPiecesCl() {
        return this.cmdNmPiecesCl;
    }

    public LigneCmdClient cmdNmPiecesCl(Integer cmdNmPiecesCl) {
        this.setCmdNmPiecesCl(cmdNmPiecesCl);
        return this;
    }

    public void setCmdNmPiecesCl(Integer cmdNmPiecesCl) {
        this.cmdNmPiecesCl = cmdNmPiecesCl;
    }

    public Set<Article> getArticles() {
        return this.articles;
    }

    public void setArticles(Set<Article> articles) {
        if (this.articles != null) {
            this.articles.forEach(i -> i.setLigneCmdClient(null));
        }
        if (articles != null) {
            articles.forEach(i -> i.setLigneCmdClient(this));
        }
        this.articles = articles;
    }

    public LigneCmdClient articles(Set<Article> articles) {
        this.setArticles(articles);
        return this;
    }

    public LigneCmdClient addArticle(Article article) {
        this.articles.add(article);
        article.setLigneCmdClient(this);
        return this;
    }

    public LigneCmdClient removeArticle(Article article) {
        this.articles.remove(article);
        article.setLigneCmdClient(null);
        return this;
    }

    public CommandeClient getCommandeClient() {
        return this.commandeClient;
    }

    public void setCommandeClient(CommandeClient commandeClient) {
        this.commandeClient = commandeClient;
    }

    public LigneCmdClient commandeClient(CommandeClient commandeClient) {
        this.setCommandeClient(commandeClient);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LigneCmdClient)) {
            return false;
        }
        return id != null && id.equals(((LigneCmdClient) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LigneCmdClient{" +
            "id=" + getId() +
            ", cmdQnCl=" + getCmdQnCl() +
            ", cmdNmPiecesCl=" + getCmdNmPiecesCl() +
            "}";
    }
}
