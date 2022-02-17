package com.nika.ebook;

import com.nika.ebook.domain.Category.Category;
import com.nika.ebook.domain.Category.ECategory;
import com.nika.ebook.domain.Role.ERole;
import com.nika.ebook.domain.Role.Role;
import com.nika.ebook.domain.book.Book;
import com.nika.ebook.domain.user.User;
import com.nika.ebook.persistance.repository.BookRepository;
import com.nika.ebook.persistance.repository.CategoryRepository;
import com.nika.ebook.persistance.repository.RoleRepository;

import com.nika.ebook.persistance.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;


@SpringBootApplication()
public class EBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBookApplication.class, args);
    }

//    @Bean
//    CommandLineRunner run(RoleRepository roleRepository, CategoryRepository categoryRepository, BookRepository bookRepository) {
//        return args -> {
//            roleRepository.save(new Role(ERole.ROLE_USER));
//            roleRepository.save(new Role(ERole.ROLE_MODERATOR));
//            roleRepository.save(new Role(ERole.ROLE_ADMIN));
//
//            categoryRepository.save(new Category(ECategory.COMICS));
//            categoryRepository.save(new Category(ECategory.NOVELS));
//            categoryRepository.save(new Category(ECategory.PROGRAMMING));
//
//        };
//    }
}
