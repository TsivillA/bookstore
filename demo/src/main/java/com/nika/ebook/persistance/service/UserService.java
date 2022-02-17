package com.nika.ebook.persistance.service;

import com.nika.ebook.domain.book.Book;
import com.nika.ebook.domain.user.User;
import com.nika.ebook.domain.user.UserEditRequst;

import java.util.List;
import java.util.Set;

public interface UserService {
     void mapToUser(User oldUser, UserEditRequst newUser);
     List<User> findAll();
     void update(long id, UserEditRequst user);
     void delete(long id);
     User getByUsername(String username);
     void addBooksToUser(Set<Book> books, String username);
     Set<Book> getUserBooks(String username);


}
