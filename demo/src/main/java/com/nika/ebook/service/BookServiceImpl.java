package com.nika.ebook.service;

import com.nika.ebook.domain.Category.Category;
import com.nika.ebook.domain.Category.ECategory;
import com.nika.ebook.domain.book.Book;
import com.nika.ebook.domain.book.BookCreateRequest;
import com.nika.ebook.domain.book.BookEditRequest;
import com.nika.ebook.persistance.repository.BookRepository;
import com.nika.ebook.persistance.repository.CategoryRepository;
import com.nika.ebook.persistance.service.BookService;
import com.nika.ebook.persistance.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final CategoryService categoryService;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public void createBook(BookCreateRequest bookRequest) {
        Book book = new Book(bookRequest.getName(), bookRequest.getAuthor(), bookRequest.getDescription(),
                bookRequest.getImageUrl(), bookRequest.getReadingUrl(), bookRequest.getPrice());

        Set<String> requestCategory = bookRequest.getCategory();
        Set<Category> categories = new HashSet<>();
        requestCategory.forEach(r -> {
            Category category = categoryService.findByRequestCategory(r);
            categories.add(category);
        });

        book.setCategory(categories);
        save(book);
    }

    @Override
    public void update(long id, BookEditRequest book) {
        Book oldBook = bookRepository.findById(id).orElseThrow(() -> new NullPointerException("Product can not be null!"));
        mapToBook(oldBook, book);
        bookRepository.save(oldBook);
    }

    @Override
    public void delete(long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cannot find book to delete"));
        book.getCategory().clear();
        bookRepository.delete(book);
    }

    @Override
    public void save(Book book) {
        if(Objects.isNull(book))
            throw new NullPointerException("Book object is null!");
        bookRepository.save(book);
    }

    @Override
    public boolean existsByName(String name) {
        return bookRepository.existsByName(name);
    }

    @Override
    public Book getByName(String name) {
        return bookRepository.findByName(name).orElse(null);
    }

    @Override
    public void mapToBook(Book oldBook, BookEditRequest newBook) {
        oldBook.setDescription(newBook.getDescription());
        oldBook.setImageUrl(newBook.getImageUrl());
        oldBook.setReadingUrl(newBook.getReadingUrl());
        oldBook.setPrice(newBook.getPrice());
    }

    @Override
    public void saveUploadedFiles(List < MultipartFile > files, long id, String filePath) throws IOException {
        for (MultipartFile file: files) {
            if (file.isEmpty()) {
                continue;
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(filePath + file.getOriginalFilename());
            Files.write(path, bytes);
            Book book = bookRepository.getById(id);
            book.setReadingUrl(path.toString());
            bookRepository.save(book);
        }
    }

    @Override
    public List<Book> getBookByCategory(String category) {
        List<Book> books = new ArrayList<>();
        for (Book book: bookRepository.findAll()) {
            for (Category cat: book.getCategory()) {
                if(Objects.equals(cat.getName().toString(),category.toUpperCase()))
                    books.add(book);
            }
        }
        return books;
    }
}
