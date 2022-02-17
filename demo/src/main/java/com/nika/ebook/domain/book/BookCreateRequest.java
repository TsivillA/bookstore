package com.nika.ebook.domain.book;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class BookCreateRequest {
    private String name;
    private String author;
    private String description;
    private String imageUrl;
    private String readingUrl;
    private BigDecimal price;
    private Set<String> category;
}
