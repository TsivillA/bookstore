package com.nika.ebook.service;

import com.nika.ebook.domain.Category.Category;
import com.nika.ebook.domain.Category.ECategory;
import com.nika.ebook.domain.book.Book;
import com.nika.ebook.domain.book.BookEditRequest;
import com.nika.ebook.persistance.repository.BookRepository;
import com.nika.ebook.persistance.repository.CategoryRepository;
import com.nika.ebook.persistance.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public void update(long id, BookEditRequest book) {
        Book oldBook = bookRepository.getById(id);
        if(Objects.isNull(oldBook)){
            throw new NullPointerException("Product can not be null!");
        }
        mapToBook(oldBook, book);

        bookRepository.save(oldBook);
    }

    @Override
    public void delete(long id) {
        Book book = bookRepository.getById(id);
        book.getCategory().clear();
        bookRepository.delete(book);
    }

    @Override
    public Book getById(Long id) {
        return bookRepository.getById(id);
    }

    @Override
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Override
    public boolean existsByName(String name) {
        if (bookRepository.existsByName(name)) return true;
        else return false;
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
        category = category.toUpperCase();
        for (Book book:
                bookRepository.findAll()) {
            for (Category cat:
                 book.getCategory()) {
                if (cat.getName().toString().equals(category)) books.add(book);
            }
        }
        return books;


    }
}
