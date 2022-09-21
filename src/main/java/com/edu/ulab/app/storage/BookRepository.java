package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.BaseIdEntity;

public interface BookRepository<E extends BaseIdEntity, K extends Number> {
    E createBook(E entity);
    E updateBook(E entity);
    E getBookById(K id);
    void deleteBookById(K id);
}
