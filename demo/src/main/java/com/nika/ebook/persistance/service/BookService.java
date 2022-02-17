package com.nika.ebook.persistance.service;

import com.nika.ebook.domain.Category.Category;
import com.nika.ebook.domain.book.Book;
import com.nika.ebook.domain.book.BookEditRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookService {
     void mapToBook(Book oldBook, BookEditRequest newBook);
     List<Book> findAll();
     void update(long id, BookEditRequest book);
     void delete(long id);
     Book getById(Long id);
     void save(Book book);
     boolean existsByName(String name);
     Book getByName(String name);
     void saveUploadedFiles(List < MultipartFile > files, long id, String filePath) throws IOException;
     List<Book> getBookByCategory(String category);

}
