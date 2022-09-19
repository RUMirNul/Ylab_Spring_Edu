package com.edu.ulab.app.dao;

import java.util.List;

public interface BookDao<E, K> {
    E createBook(E entity);
    E updateBook(E entity);
    E getBookById(K id);
    void deleteBookById(K id);
    List<Long> getAllBookIdByUserId(K id);
}
