package com.nika.ebook.service;

import com.nika.ebook.domain.book.Book;
import com.nika.ebook.domain.user.User;
import com.nika.ebook.domain.user.UserEditRequst;
import com.nika.ebook.persistance.repository.UserRepository;
import com.nika.ebook.persistance.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void update(long id, UserEditRequst newUser) {
        User oldUser = userRepository.getById(id);
        if(Objects.isNull(oldUser)){
            throw new NullPointerException("Product can not be null!");
        }
        mapToUser(oldUser, newUser);

        userRepository.save(oldUser);
    }

    @Override
    public void delete(long id) {
        User user = userRepository.getById(id);
        userRepository.delete(user);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public void addBooksToUser(Set<Book> books, String username) {
//        userRepository.findByUsername(username).get().setBooks(books);
        User user = userRepository.findByUsername(username).orElse(null);
        Set<Book> userBooks = user.getBooks();
        userBooks.addAll(books);
        user.setBooks(userBooks);
        userRepository.save(user);
    }

    @Override
    public Set<Book> getUserBooks(String username) {
        return userRepository.findByUsername(username).orElse(null).getBooks();
    }


    @Override
    public void mapToUser(User oldUser, UserEditRequst newUser) {
        oldUser.setUsername(newUser.getUsername());
        oldUser.setEmail(newUser.getEmail());
    }
}
