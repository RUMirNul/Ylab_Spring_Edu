package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.storage.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Slf4j
public class BookRepositoryImpl implements BookRepository<BookEntity, Long> {

    private static AtomicLong id;
    private final Map<Long, BookEntity> bookIdAndBookEntityMap;

    public BookRepositoryImpl() {
        id = new AtomicLong(0);
        this.bookIdAndBookEntityMap = new ConcurrentHashMap<>();
    }


    @Override
    public BookEntity createBook(BookEntity entity) {
        log.info("Create book by entity: {}", entity);

        entity.setId(getNextId());

        bookIdAndBookEntityMap.put(entity.getId(), entity);

        BookEntity copyStorageIndependentBookEntity = createStorageIndependentBookEntity(entity);
        log.info("Created book: {}", copyStorageIndependentBookEntity);

        return copyStorageIndependentBookEntity;
    }

    @Override
    public BookEntity updateBook(BookEntity entity) {
        log.info("Update book by entity: {}", entity);

        Long id = entity.getId();
        log.info("Book id for update: {}", id);

        if (id != null && bookIdAndBookEntityMap.containsKey(id)) {
            BookEntity tempBookEntity = bookIdAndBookEntityMap.get(id);
            tempBookEntity.setUserId(entity.getUserId());
            tempBookEntity.setAuthor(entity.getAuthor());
            tempBookEntity.setTitle(entity.getTitle());
            tempBookEntity.setPageCount(entity.getPageCount());

            BookEntity copyStorageIndependentBookEntity = createStorageIndependentBookEntity(entity);
            log.info("Created book: {}", copyStorageIndependentBookEntity);
            return copyStorageIndependentBookEntity;
        }

        return null;
    }

    @Override
    public BookEntity getBookById(Long id) {
        log.info("Got book by id: {}", id);

        if (bookIdAndBookEntityMap.get(id) != null) {

            BookEntity copyStorageIndependentBookEntity = createStorageIndependentBookEntity(bookIdAndBookEntityMap.get(id));
            log.info("Got book: {}", copyStorageIndependentBookEntity);

            return copyStorageIndependentBookEntity;
        }

        return null;
    }

    @Override
    public void deleteBookById(Long id) {
        log.info("Delete book by id: {}", id);

        if (id != null) {
            BookEntity deletedBook = bookIdAndBookEntityMap.remove(id);
            log.info("Deleted book: {}", deletedBook);
        }
    }

    private BookEntity createStorageIndependentBookEntity(BookEntity entity) {
        if (entity == null) return null;

        return BookEntity.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .title(entity.getTitle())
                .author(entity.getAuthor())
                .pageCount(entity.getPageCount())
                .build();
    }

    private Long getNextId() {
        return id.getAndIncrement();
    }
}
