package com.hbo.mycrmv1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Article.
 */
@Entity
@Table(name = "article")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "artcl_iden")
    private Integer artclIden;

    @Column(name = "artcl_reference")
    private String artclReference;

    @Column(name = "artcl_designation")
    private String artclDesignation;

    @Column(name = "artcl_qn_stock")
    private Integer artclQnStock;

    @Lob
    @Column(name = "artcl_img")
    private byte[] artclImg;

    @Column(name = "artcl_img_content_type")
    private String artclImgContentType;

    @Column(name = "artcl_serie")
    private String artclSerie;

    @Column(name = "artcl_prix_achat")
    private Double artclPrixAchat;

    @Column(name = "artcl_px_achat_total")
    private Double artclPxAchatTotal;

    @Column(name = "artcl_px_vente_total")
    private Double artclPxVenteTotal;

    @ManyToOne
    @JsonIgnoreProperties(value = { "articles", "commandeFourniseur" }, allowSetters = true)
    private LigneCmdFournisseur ligneCmdFournisseur;

    @ManyToOne
    @JsonIgnoreProperties(value = { "articles", "livraisonFr" }, allowSetters = true)
    private LigneLivFournisseur ligneLivFournisseur;

    @ManyToOne
    @JsonIgnoreProperties(value = { "articles" }, allowSetters = true)
    private UniteArticle uniteArticle;

    @ManyToOne
    @JsonIgnoreProperties(value = { "articles", "commandeClient" }, allowSetters = true)
    private LigneCmdClient ligneCmdClient;

    @ManyToOne
    @JsonIgnoreProperties(value = { "articles", "livraisonCl" }, allowSetters = true)
    private LigneLivClient ligneLivClient;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Article id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getArtclIden() {
        return this.artclIden;
    }

    public Article artclIden(Integer artclIden) {
        this.setArtclIden(artclIden);
        return this;
    }

    public void setArtclIden(Integer artclIden) {
        this.artclIden = artclIden;
    }

    public String getArtclReference() {
        return this.artclReference;
    }

    public Article artclReference(String artclReference) {
        this.setArtclReference(artclReference);
        return this;
    }

    public void setArtclReference(String artclReference) {
        this.artclReference = artclReference;
    }

    public String getArtclDesignation() {
        return this.artclDesignation;
    }

    public Article artclDesignation(String artclDesignation) {
        this.setArtclDesignation(artclDesignation);
        return this;
    }

    public void setArtclDesignation(String artclDesignation) {
        this.artclDesignation = artclDesignation;
    }

    public Integer getArtclQnStock() {
        return this.artclQnStock;
    }

    public Article artclQnStock(Integer artclQnStock) {
        this.setArtclQnStock(artclQnStock);
        return this;
    }

    public void setArtclQnStock(Integer artclQnStock) {
        this.artclQnStock = artclQnStock;
    }

    public byte[] getArtclImg() {
        return this.artclImg;
    }

    public Article artclImg(byte[] artclImg) {
        this.setArtclImg(artclImg);
        return this;
    }

    public void setArtclImg(byte[] artclImg) {
        this.artclImg = artclImg;
    }

    public String getArtclImgContentType() {
        return this.artclImgContentType;
    }

    public Article artclImgContentType(String artclImgContentType) {
        this.artclImgContentType = artclImgContentType;
        return this;
    }

    public void setArtclImgContentType(String artclImgContentType) {
        this.artclImgContentType = artclImgContentType;
    }

    public String getArtclSerie() {
        return this.artclSerie;
    }

    public Article artclSerie(String artclSerie) {
        this.setArtclSerie(artclSerie);
        return this;
    }

    public void setArtclSerie(String artclSerie) {
        this.artclSerie = artclSerie;
    }

    public Double getArtclPrixAchat() {
        return this.artclPrixAchat;
    }

    public Article artclPrixAchat(Double artclPrixAchat) {
        this.setArtclPrixAchat(artclPrixAchat);
        return this;
    }

    public void setArtclPrixAchat(Double artclPrixAchat) {
        this.artclPrixAchat = artclPrixAchat;
    }

    public Double getArtclPxAchatTotal() {
        return this.artclPxAchatTotal;
    }

    public Article artclPxAchatTotal(Double artclPxAchatTotal) {
        this.setArtclPxAchatTotal(artclPxAchatTotal);
        return this;
    }

    public void setArtclPxAchatTotal(Double artclPxAchatTotal) {
        this.artclPxAchatTotal = artclPxAchatTotal;
    }

    public Double getArtclPxVenteTotal() {
        return this.artclPxVenteTotal;
    }

    public Article artclPxVenteTotal(Double artclPxVenteTotal) {
        this.setArtclPxVenteTotal(artclPxVenteTotal);
        return this;
    }

    public void setArtclPxVenteTotal(Double artclPxVenteTotal) {
        this.artclPxVenteTotal = artclPxVenteTotal;
    }

    public LigneCmdFournisseur getLigneCmdFournisseur() {
        return this.ligneCmdFournisseur;
    }

    public void setLigneCmdFournisseur(LigneCmdFournisseur ligneCmdFournisseur) {
        this.ligneCmdFournisseur = ligneCmdFournisseur;
    }

    public Article ligneCmdFournisseur(LigneCmdFournisseur ligneCmdFournisseur) {
        this.setLigneCmdFournisseur(ligneCmdFournisseur);
        return this;
    }

    public LigneLivFournisseur getLigneLivFournisseur() {
        return this.ligneLivFournisseur;
    }

    public void setLigneLivFournisseur(LigneLivFournisseur ligneLivFournisseur) {
        this.ligneLivFournisseur = ligneLivFournisseur;
    }

    public Article ligneLivFournisseur(LigneLivFournisseur ligneLivFournisseur) {
        this.setLigneLivFournisseur(ligneLivFournisseur);
        return this;
    }

    public UniteArticle getUniteArticle() {
        return this.uniteArticle;
    }

    public void setUniteArticle(UniteArticle uniteArticle) {
        this.uniteArticle = uniteArticle;
    }

    public Article uniteArticle(UniteArticle uniteArticle) {
        this.setUniteArticle(uniteArticle);
        return this;
    }

    public LigneCmdClient getLigneCmdClient() {
        return this.ligneCmdClient;
    }

    public void setLigneCmdClient(LigneCmdClient ligneCmdClient) {
        this.ligneCmdClient = ligneCmdClient;
    }

    public Article ligneCmdClient(LigneCmdClient ligneCmdClient) {
        this.setLigneCmdClient(ligneCmdClient);
        return this;
    }

    public LigneLivClient getLigneLivClient() {
        return this.ligneLivClient;
    }

    public void setLigneLivClient(LigneLivClient ligneLivClient) {
        this.ligneLivClient = ligneLivClient;
    }

    public Article ligneLivClient(LigneLivClient ligneLivClient) {
        this.setLigneLivClient(ligneLivClient);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Article)) {
            return false;
        }
        return id != null && id.equals(((Article) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Article{" +
            "id=" + getId() +
            ", artclIden=" + getArtclIden() +
            ", artclReference='" + getArtclReference() + "'" +
            ", artclDesignation='" + getArtclDesignation() + "'" +
            ", artclQnStock=" + getArtclQnStock() +
            ", artclImg='" + getArtclImg() + "'" +
            ", artclImgContentType='" + getArtclImgContentType() + "'" +
            ", artclSerie='" + getArtclSerie() + "'" +
            ", artclPrixAchat=" + getArtclPrixAchat() +
            ", artclPxAchatTotal=" + getArtclPxAchatTotal() +
            ", artclPxVenteTotal=" + getArtclPxVenteTotal() +
            "}";
    }
}
