package com.nika.ebook.api.controller;

import com.nika.ebook.api.constants.SwaggerConstant;
import com.nika.ebook.domain.Category.Category;
import com.nika.ebook.domain.Category.ECategory;
import com.nika.ebook.domain.MessageResponse;

import com.nika.ebook.domain.book.Book;
import com.nika.ebook.domain.book.BookCreateRequest;
import com.nika.ebook.domain.book.BookEditRequest;
import com.nika.ebook.persistance.repository.CategoryRepository;
import com.nika.ebook.persistance.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.nika.ebook.api.constants.AllowedOrigins.ALLOWED_ORIGIN;
import static com.nika.ebook.api.constants.ResponseConstants.*;
import static com.nika.ebook.api.constants.RouteConstants.*;
import static com.nika.ebook.api.constants.SwaggerConstant.*;


@RestController
@CrossOrigin(origins = ALLOWED_ORIGIN, allowCredentials = "true")
@RequestMapping(BOOKS_API)
@RequiredArgsConstructor
@Api(tags = {SwaggerConstant.BOOK_TAG})
public class BookController {
    private final BookService bookService;

    @Value("${book.upload.folder}")
    private String uploadFolder;


    @ApiOperation(value = "Create book", notes = "Create and save book in the system")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "The book has been created successfully"),
            @ApiResponse(responseCode = "400", description = RESPONSE_400),
            @ApiResponse(responseCode = "404", description = RESPONSE_404),
            @ApiResponse(responseCode = "500", description = RESPONSE_500),
            @ApiResponse(responseCode = "403", description = RESPONSE_403),
            @ApiResponse(responseCode = "401", description = RESPONSE_401),
    })
    @PostMapping(CREATE)
    public ResponseEntity<?> createBook(@RequestBody BookCreateRequest bookRequest) {
        if(bookService.existsByName(bookRequest.getName())){
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Book exists!"));
        }

        bookService.createBook(bookRequest);
        return ResponseEntity.ok(new MessageResponse("Book CREATED"));
    }

    @ApiOperation(value = "Get all book", notes = "Get all the books from the system")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "The books have been retrieved successfully"),
            @ApiResponse(responseCode = "400", description = RESPONSE_400),
            @ApiResponse(responseCode = "404", description = RESPONSE_404),
            @ApiResponse(responseCode = "500", description = RESPONSE_500),
            @ApiResponse(responseCode = "403", description = RESPONSE_403),
            @ApiResponse(responseCode = "401", description = RESPONSE_401),
    })
    @GetMapping(GET_BOOKS_ENDPOINT)
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok().body(bookService.findAll());
    }

    @ApiOperation(value = "Get the book", notes = "Get the book from the system by name")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "The book has been retrieved successfully"),
            @ApiResponse(responseCode = "400", description = RESPONSE_400),
            @ApiResponse(responseCode = "404", description = RESPONSE_404),
            @ApiResponse(responseCode = "500", description = RESPONSE_500),
            @ApiResponse(responseCode = "403", description = RESPONSE_403),
            @ApiResponse(responseCode = "401", description = RESPONSE_401),
    })
    @GetMapping(GET_BOOK_BY_NAME)
    public ResponseEntity<Book> getBookByName(@PathVariable String name) {
        Book book = bookService.getByName(name);
        if (Objects.isNull(book)) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @ApiOperation(value = "Get the book", notes = "Get the book from the system by category")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "The books have been retrieved successfully"),
            @ApiResponse(responseCode = "400", description = RESPONSE_400),
            @ApiResponse(responseCode = "404", description = RESPONSE_404),
            @ApiResponse(responseCode = "500", description = RESPONSE_500),
            @ApiResponse(responseCode = "403", description = RESPONSE_403),
            @ApiResponse(responseCode = "401", description = RESPONSE_401),
    })
    @GetMapping(GET_BOOK_BY_CATEGORY )
    public ResponseEntity<List<Book>> getBookByCategory(@PathVariable String category) {
        return ResponseEntity.ok().body(bookService.getBookByCategory(category));
    }


    @ApiOperation(value = "Update the book", notes = "Update the book in the system by id")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "The book has been updated successfully"),
            @ApiResponse(responseCode = "400", description = RESPONSE_400),
            @ApiResponse(responseCode = "404", description = RESPONSE_404),
            @ApiResponse(responseCode = "500", description = RESPONSE_500),
            @ApiResponse(responseCode = "403", description = RESPONSE_403),
            @ApiResponse(responseCode = "401", description = RESPONSE_401),
    })
    @PutMapping(UPDATE_BY_ID)
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody BookEditRequest book) {
        bookService.update(id, book);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Delete the book", notes = "Delete the book in the system by id")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "The book has been deleted successfully"),
            @ApiResponse(responseCode = "400", description = RESPONSE_400),
            @ApiResponse(responseCode = "404", description = RESPONSE_404),
            @ApiResponse(responseCode = "500", description = RESPONSE_500),
            @ApiResponse(responseCode = "403", description = RESPONSE_403),
            @ApiResponse(responseCode = "401", description = RESPONSE_401),
    })
    @DeleteMapping(DELETE_BY_ID)
    public ResponseEntity<?> delete(@PathVariable long id) {
        bookService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @PostMapping(UPLOAD_FILE)
    public ResponseEntity<?> uploadFile(@RequestPart("file") MultipartFile uploadfile, @PathVariable long id) {
        if (uploadfile.isEmpty()) {
            return new ResponseEntity<>("You must select a file!", HttpStatus.OK);
        }
        try {
            bookService.saveUploadedFiles(Collections.singletonList(uploadfile), id, uploadFolder);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Successfully uploaded - " + uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(GET_FILE_PDF)
    public ResponseEntity<InputStreamResource> getBookPdf(@PathVariable String filename){
        File file = new File(uploadFolder + filename);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline;filename=\"" +filename + "\"");

        InputStreamResource resource;
        try {
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException fileNotFoundException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }
}
