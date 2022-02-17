package com.nika.ebook.persistance.repository;

import com.nika.ebook.domain.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Boolean existsByName(String name);
    Optional<Book> findByName(String book);
}
