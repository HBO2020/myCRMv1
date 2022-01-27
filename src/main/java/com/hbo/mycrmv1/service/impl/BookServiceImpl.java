package com.hbo.mycrmv1.service.impl;

import com.hbo.mycrmv1.domain.Book;
import com.hbo.mycrmv1.repository.BookRepository;
import com.hbo.mycrmv1.service.BookService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Book}.
 */
@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book save(Book book) {
        log.debug("Request to save Book : {}", book);
        return bookRepository.save(book);
    }

    @Override
    public Optional<Book> partialUpdate(Book book) {
        log.debug("Request to partially update Book : {}", book);

        return bookRepository
            .findById(book.getId())
            .map(existingBook -> {
                if (book.getBookName() != null) {
                    existingBook.setBookName(book.getBookName());
                }
                if (book.getAuthorName() != null) {
                    existingBook.setAuthorName(book.getAuthorName());
                }
                if (book.getNomOFBooks() != null) {
                    existingBook.setNomOFBooks(book.getNomOFBooks());
                }
                if (book.getIsDnNomber() != null) {
                    existingBook.setIsDnNomber(book.getIsDnNomber());
                }
                if (book.getSubjectBook() != null) {
                    existingBook.setSubjectBook(book.getSubjectBook());
                }
                if (book.getLangOfBook() != null) {
                    existingBook.setLangOfBook(book.getLangOfBook());
                }

                return existingBook;
            })
            .map(bookRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        log.debug("Request to get all Books");
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findOne(Long id) {
        log.debug("Request to get Book : {}", id);
        return bookRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Book : {}", id);
        bookRepository.deleteById(id);
    }
}
