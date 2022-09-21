package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dao.BookDao;
import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final BookDao<BookEntity, Long> bookDao;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookDao<BookEntity, Long> bookDao, BookMapper bookMapper) {
        this.bookDao = bookDao;
        this.bookMapper = bookMapper;
    }

    @Override
    public BookDto createBook(BookDto bookDto) {
        log.info("Got create book by book DTO: {}", bookDto);

        BookDto createdBook = bookMapper.bookEntityToBookDto(bookDao.createBook(bookMapper.bookDtoToBookEntity(bookDto)));
        log.info("Created book: {}", createdBook);

        return createdBook;
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        log.info("Got update book by book DTO: {}", bookDto);

        BookDto updatedBook = bookMapper.bookEntityToBookDto(bookDao.updateBook(bookMapper.bookDtoToBookEntity(bookDto)));
        log.info("Updated book: {}", updatedBook);

        return updatedBook;
    }

    @Override
    public BookDto getBookById(Long id) {
        log.info("Wants get book by book id: {}", id);

        BookDto receivedBook = bookMapper.bookEntityToBookDto(bookDao.getBookById(id));
        log.info("Received book: {}", receivedBook);

        return receivedBook;
    }

    @Override
    public void deleteBookById(Long id) {
        log.info("Got delete book by book id: {}", id);

        bookDao.deleteBookById(id);
    }

    @Override
    public List<Long> getAllBookIdByUserId(Long id) {
        log.info("Wants get all books by user id: {}", id);

        List<Long> allBooksByUserId = bookDao.getAllBookIdByUserId(id);
        log.info("Received all books by user id: {}", allBooksByUserId);

        return bookDao.getAllBookIdByUserId(id);
    }
}
