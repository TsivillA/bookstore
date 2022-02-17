package com.nika.ebook.api.controller;

import com.nika.ebook.api.constants.SwaggerConstant;
import com.nika.ebook.domain.book.Book;
import com.nika.ebook.domain.user.User;
import com.nika.ebook.domain.user.UserEditRequst;
import com.nika.ebook.persistance.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.nika.ebook.api.constants.SwaggerConstant.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200/", allowCredentials = "true")
@RequestMapping("/api")
@RequiredArgsConstructor
@Api(tags = {SwaggerConstant.USER_TAG})
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "Get all users", notes = "Retrieve all users from the system")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "The users have been retrieved successfully"),
            @ApiResponse(responseCode = "400", description = RESPONSE_400),
            @ApiResponse(responseCode = "404", description = RESPONSE_404),
            @ApiResponse(responseCode = "500", description = RESPONSE_500),
            @ApiResponse(responseCode = "403", description = RESPONSE_403),
            @ApiResponse(responseCode = "401", description = RESPONSE_401),
    })
    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @ApiOperation(value = "Get user by username", notes = "Retrieve the user by username from the system")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "The user has been retrieved successfully"),
            @ApiResponse(responseCode = "400", description = RESPONSE_400),
            @ApiResponse(responseCode = "404", description = RESPONSE_404),
            @ApiResponse(responseCode = "500", description = RESPONSE_500),
            @ApiResponse(responseCode = "403", description = RESPONSE_403),
            @ApiResponse(responseCode = "401", description = RESPONSE_401),
    })
    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @ApiOperation(value = "Update user", notes = "Update user with id")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "The user has been updated successfully"),
            @ApiResponse(responseCode = "400", description = RESPONSE_400),
            @ApiResponse(responseCode = "404", description = RESPONSE_404),
            @ApiResponse(responseCode = "500", description = RESPONSE_500),
            @ApiResponse(responseCode = "403", description = RESPONSE_403),
            @ApiResponse(responseCode = "401", description = RESPONSE_401),
    })
    @PutMapping("/users/update/{id}")
    public ResponseEntity<?> update(@PathVariable long id, @RequestBody UserEditRequst user) {
        userService.update(id, user);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Delete user", notes = "Delete user from the system with id")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "The user has been deleted successfully"),
            @ApiResponse(responseCode = "400", description = RESPONSE_400),
            @ApiResponse(responseCode = "404", description = RESPONSE_404),
            @ApiResponse(responseCode = "500", description = RESPONSE_500),
            @ApiResponse(responseCode = "403", description = RESPONSE_403),
            @ApiResponse(responseCode = "401", description = RESPONSE_401),
    })
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        userService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "Get books of user", notes = "Retrieve the the books of user by username")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "The user has been retrieved successfully"),
            @ApiResponse(responseCode = "400", description = RESPONSE_400),
            @ApiResponse(responseCode = "404", description = RESPONSE_404),
            @ApiResponse(responseCode = "500", description = RESPONSE_500),
            @ApiResponse(responseCode = "403", description = RESPONSE_403),
            @ApiResponse(responseCode = "401", description = RESPONSE_401),
    })
    @GetMapping("/users/books/{username}")
    public ResponseEntity<?> getUserBooks(@PathVariable String username) {
        Set<Book> books = userService.getByUsername(username).getBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @ApiOperation(value = "Add books to user", notes = "Retrieve the the books of user by username")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "The user has been retrieved successfully"),
            @ApiResponse(responseCode = "400", description = RESPONSE_400),
            @ApiResponse(responseCode = "404", description = RESPONSE_404),
            @ApiResponse(responseCode = "500", description = RESPONSE_500),
            @ApiResponse(responseCode = "403", description = RESPONSE_403),
            @ApiResponse(responseCode = "401", description = RESPONSE_401),
    })
    @PostMapping("/users/books/{username}")
    public ResponseEntity<?> addBooksToUser(@PathVariable String username, @RequestBody Set<Book> books) {
        userService.addBooksToUser(books, username);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

}

