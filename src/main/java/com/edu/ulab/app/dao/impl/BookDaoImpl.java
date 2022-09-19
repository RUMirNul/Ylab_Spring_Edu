package com.edu.ulab.app.dao.impl;

import com.edu.ulab.app.dao.BookDao;
import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.storage.BookStorage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDaoImpl implements BookDao<BookEntity, Long> {

    private final BookStorage<BookEntity, Long> storage;

    public BookDaoImpl(BookStorage<BookEntity, Long> storage) {
        this.storage = storage;
    }

    @Override
    public BookEntity createBook(BookEntity entity) {
        return storage.createBook(entity);
    }

    @Override
    public BookEntity updateBook(BookEntity entity) {
        return storage.updateBook(entity);
    }

    @Override
    public BookEntity getBookById(Long id) {
        return storage.getBookById(id);
    }

    @Override
    public void deleteBookById(Long id) {
        storage.deleteBookById(id);
    }

    @Override
    public List<Long> getAllBookIdByUserId(Long id) {
        return storage.getAllBookIdByUserId(id);
    }
}
