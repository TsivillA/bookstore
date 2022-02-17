package com.nika.ebook.domain.book;

import com.nika.ebook.domain.Category.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "books",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "name"),
        })
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String author;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> category = new HashSet<>();

    @NotNull
    @NotEmpty
    private String description;

    private String imageUrl;

    private String readingUrl;

    @NotNull
    @NotEmpty
    private BigDecimal price;

    public Book(String name,String author, String description, String imageUrl, String readingUrl, BigDecimal price) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.imageUrl = imageUrl;
        this.readingUrl = readingUrl;
        this.price = price;
    }

}
