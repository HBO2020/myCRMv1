package com.hbo.mycrmv1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Catalogue.
 */
@Entity
@Table(name = "catalogue")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Catalogue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "nom_of_copies")
    private Integer nomOfCopies;

    @ManyToMany
    @JoinTable(
        name = "rel_catalogue__book",
        joinColumns = @JoinColumn(name = "catalogue_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "staff", "members", "catalogues" }, allowSetters = true)
    private Set<Book> books = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Catalogue id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public Catalogue authorName(String authorName) {
        this.setAuthorName(authorName);
        return this;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getNomOfCopies() {
        return this.nomOfCopies;
    }

    public Catalogue nomOfCopies(Integer nomOfCopies) {
        this.setNomOfCopies(nomOfCopies);
        return this;
    }

    public void setNomOfCopies(Integer nomOfCopies) {
        this.nomOfCopies = nomOfCopies;
    }

    public Set<Book> getBooks() {
        return this.books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Catalogue books(Set<Book> books) {
        this.setBooks(books);
        return this;
    }

    public Catalogue addBook(Book book) {
        this.books.add(book);
        book.getCatalogues().add(this);
        return this;
    }

    public Catalogue removeBook(Book book) {
        this.books.remove(book);
        book.getCatalogues().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Catalogue)) {
            return false;
        }
        return id != null && id.equals(((Catalogue) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Catalogue{" +
            "id=" + getId() +
            ", authorName='" + getAuthorName() + "'" +
            ", nomOfCopies=" + getNomOfCopies() +
            "}";
    }
}
