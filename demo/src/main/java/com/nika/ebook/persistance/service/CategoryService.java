package com.nika.ebook.persistance.service;

import com.nika.ebook.domain.Category.Category;
import com.nika.ebook.domain.Category.ECategory;

import java.util.Optional;

public interface CategoryService {
    Category findByRequestCategory(String requestCategory);
    Optional<Category> findByName(ECategory name);
}
