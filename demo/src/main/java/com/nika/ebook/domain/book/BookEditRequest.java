package com.nika.ebook.domain.book;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookEditRequest {
    private String description;
    private String imageUrl;
    private String readingUrl;
    private BigDecimal price;
}
