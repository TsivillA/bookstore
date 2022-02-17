package com.nika.ebook.persistance.repository;

import com.nika.ebook.domain.Category.Category;
import com.nika.ebook.domain.Category.ECategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(ECategory name);
}
