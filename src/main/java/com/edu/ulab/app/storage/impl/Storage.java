package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.storage.BookRepository;
import com.edu.ulab.app.storage.BookStorage;
import com.edu.ulab.app.storage.UserRepository;
import com.edu.ulab.app.storage.UserStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
@Slf4j
public class Storage implements UserStorage<UserEntity, Long>, BookStorage<BookEntity, Long> {

    private final UserRepository<UserEntity, Long> userRepository;
    private final BookRepository<BookEntity, Long> bookRepository;

    private final Map<Long, List<Long>> userIdAndBookIds;

    public Storage(UserRepository<UserEntity, Long> userRepository, BookRepository<BookEntity, Long> bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.userIdAndBookIds = new ConcurrentHashMap<>();
    }

    @Override
    public UserEntity createUser(UserEntity entity) {
        log.info("Creating a User by Entity: {}", entity);

        UserEntity returnedEntity = userRepository.createUser(entity);
        userIdAndBookIds.put(returnedEntity.getId(), new CopyOnWriteArrayList<>());
        log.info("Created user: {}", returnedEntity);

        return returnedEntity;
    }

    @Override
    public UserEntity updateUser(UserEntity entity) {
        log.info("Update User by Entity: {}", entity);

        Long id = entity.getId();
        log.info("User id: {}", id);

        if (id != null && userIdAndBookIds.containsKey(id)) {
            userIdAndBookIds.remove(id);
            userIdAndBookIds.put(id, new CopyOnWriteArrayList<>());

            UserEntity updatedUser = userRepository.updateUser(entity);
            log.info("Updated user: {}", updatedUser);

            return updatedUser;
        }

        return createUser(entity);
    }

    @Override
    public UserEntity getUserById(Long id) {
        log.info("Get user entity by id: {}", id);

        UserEntity userEntity = userRepository.getUserById(id);
        log.info("Got user: {}", userEntity);

        return userEntity;
    }

    @Override
    public void deleteUserById(Long id) {
        log.info("Delete user with id: {}", id);

        if (id != null && userIdAndBookIds.containsKey(id)) {
            List<Long> bookIdList = userIdAndBookIds.get(id);
            log.info("Books for delete with user: {}", bookIdList);

            for (Long bookId : bookIdList) {
                BookEntity tempBook = bookRepository.getBookById(bookId);
                tempBook.setUserId(null);
                bookRepository.updateBook(tempBook);
            }

            userRepository.deleteUserById(id);
            userIdAndBookIds.remove(id);
            log.info("User was delete with id: {}", id);
        }
    }

    @Override
    public BookEntity createBook(BookEntity entity) {
        log.info("Create book by Entity: {}", entity);

        BookEntity tempEntity = bookRepository.createBook(entity);
        Long userId = tempEntity.getUserId();

        log.info("Created book: {}", tempEntity);
        userIdAndBookIds.get(userId).add(tempEntity.getId());

        return tempEntity;
    }

    @Override
    public BookEntity updateBook(BookEntity entity) {
        log.info("Got book update by Entity: {}", entity);

        BookEntity updatedBook = bookRepository.updateBook(entity);
        log.info("Updated book: {}", updatedBook);

        return updatedBook;
    }

    @Override
    public BookEntity getBookById(Long id) {
        log.info("Get book by id: {}", id);

        BookEntity gotBook = bookRepository.getBookById(id);
        log.info("Got book: {}", gotBook);

        return gotBook;
    }

    @Override
    public void deleteBookById(Long id) {
        log.info("Got delete book with id: {}", id);
        if (id != null) {

            BookEntity tempBookEntity = bookRepository.getBookById(id);
            log.info("Book for delete: {}", tempBookEntity);

            if (tempBookEntity != null) {
                Long userId = tempBookEntity.getUserId();

                if (userId != null && userIdAndBookIds.containsKey(userId)) {
                    userIdAndBookIds.get(userId).remove(tempBookEntity.getId());
                }

                bookRepository.deleteBookById(id);
                log.info("Book was deleted with id: {}", id);
            }
        }
    }

    @Override
    public List<Long> getAllBookIdByUserId(Long id) {
        log.info("Get all book with user id: {}", id);

        List<Long> booksIdByUserId = new ArrayList<>();

        if (id != null && userIdAndBookIds.containsKey(id)) {
            booksIdByUserId = new ArrayList<>(userIdAndBookIds.get(id));
            log.info("All book id by user id: {}", booksIdByUserId);

            return booksIdByUserId;
        }

        log.info("All book id by user id: {}", booksIdByUserId);
        return booksIdByUserId;
    }

}
