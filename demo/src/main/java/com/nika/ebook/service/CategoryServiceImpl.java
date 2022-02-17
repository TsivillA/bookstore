package com.nika.ebook.service;

import com.nika.ebook.domain.Category.Category;
import com.nika.ebook.domain.Category.ECategory;
import com.nika.ebook.persistance.repository.CategoryRepository;
import com.nika.ebook.persistance.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category findByRequestCategory(String requestCategory) {
        Category category;
        switch (requestCategory) {
            case "comics":
                category = findByName(ECategory.COMICS)
                        .orElseThrow(() -> new RuntimeException("Error: Category 'COMICS' Not Found!"));
                break;
            case "novels":
                category = findByName(ECategory.NOVELS)
                        .orElseThrow(() -> new RuntimeException("Error: Category 'NOVELS' Not Found!"));
                break;
            default:
                category = findByName(ECategory.PROGRAMMING)
                        .orElseThrow(() -> new RuntimeException("Error: Category 'PROGRAMMING' Not Found!"));
        }
        return category;
    }

    @Override
    public Optional<Category> findByName(ECategory name) {
        return categoryRepository.findByName(name);
    }
}
