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
        return bookMapper.bookEntityToBookDto(bookDao.createBook(bookMapper.bookDtoToBookEntity(bookDto)));
    }

    @Override
    public BookDto updateBook(BookDto bookDto) {
        return bookMapper.bookEntityToBookDto(bookDao.updateBook(bookMapper.bookDtoToBookEntity(bookDto)));
    }

    @Override
    public BookDto getBookById(Long id) {
        return bookMapper.bookEntityToBookDto(bookDao.getBookById(id));
    }

    @Override
    public void deleteBookById(Long id) {
        bookDao.deleteBookById(id);
    }

    @Override
    public List<Long> getAllBookIdByUserId(Long id) {
        return bookDao.getAllBookIdByUserId(id);
    }
}
