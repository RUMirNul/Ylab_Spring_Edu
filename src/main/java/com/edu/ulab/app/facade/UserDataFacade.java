package com.edu.ulab.app.facade;

import com.edu.ulab.app.constant.ErrorMessageTextConstants;
import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.web.request.BookRequest;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.response.UserBookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class UserDataFacade {
    private final UserService userService;
    private final BookService bookService;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    public UserDataFacade(UserService userService,
                          BookService bookService,
                          UserMapper userMapper,
                          BookMapper bookMapper) {
        this.userService = userService;
        this.bookService = bookService;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {
        log.info("Got user book create request: {}", userBookRequest);

        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        if (userDto == null) throw new NullPointerException(ErrorMessageTextConstants.USER_CAN_NOT_BE_NULL);

        UserDto createdUser = userService.createUser(userDto);
        log.info("Created user: {}", createdUser);

        List<BookRequest> bookRequest = userBookRequest.getBookRequests();
        if (bookRequest == null) throw new NullPointerException(ErrorMessageTextConstants.BOOK_LIST_CAN_NOT_BE_NULL);

        List<Long> bookIdList = bookRequest
                .stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(createdUser.getId()))
                .peek(mappedBookDto -> log.info("Mapped book: {}", mappedBookDto))
                .map(bookService::createBook)
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(createdUser.getId())
                .booksIdList(bookIdList)
                .build();
    }


    public UserBookResponse updateUserWithBooks(UserBookRequest userBookRequest, Long userId) {
        log.info("Got user book update request: {}, userId = {}", userBookRequest, userId);
        if (userId == null) throw new NullPointerException(ErrorMessageTextConstants.USER_CAN_NOT_BE_NULL);

        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        if (userDto == null) throw new NullPointerException(ErrorMessageTextConstants.USER_CAN_NOT_BE_NULL);

        if (userService.getUserById(userId) != null) {
            userDto.setId(userId);
            UserDto updatedUser = userService.updateUser(userDto);
            log.info("Updated user: {}", updatedUser);

            List<BookRequest> bookRequest = userBookRequest.getBookRequests();
            if (bookRequest == null) throw new NullPointerException(ErrorMessageTextConstants.BOOK_LIST_CAN_NOT_BE_NULL);

            bookRequest.stream()
                    .filter(Objects::nonNull)
                    .map(bookMapper::bookRequestToBookDto)
                    .peek(bookDto -> bookDto.setUserId(userId))
                    .peek(mappedBookDto -> log.info("Mapped book: {}", mappedBookDto))
                    .forEach(bookService::createBook);

            List<Long> userBookIdList = bookService.getAllBookIdByUserId(userId);
            log.info("Collected book ids: {}", userBookIdList);

            return UserBookResponse.builder()
                    .userId(userId)
                    .booksIdList(userBookIdList)
                    .build();

        } else {
            //Если нет пользователя с нужным id, то создаётся новый
            UserBookResponse newUser = createUserWithBooks(userBookRequest);
            log.info("No user with the required id was found. Therefore, a new user was created: {}", newUser);

            return newUser;
        }
    }

    public UserBookResponse getUserWithBooks(Long userId) {
        log.info("Got user book get request with userId: {}", userId);
        if (userId == null) throw new NullPointerException(ErrorMessageTextConstants.USER_ID_CAN_NOT_BE_NULL);

        UserDto userDto = userService.getUserById(userId);
        log.info("Got user: {}", userDto);

        if (userDto == null) throw new NullPointerException("No have user with this id: " + userId);

        return UserBookResponse.builder()
                .userId(userDto.getId())
                .booksIdList(bookService.getAllBookIdByUserId(userId))
                .build();
    }

    public void deleteUserWithBooks(Long userId) {
        log.info("Got user book delete request with user id: {}", userId);

        if (userId != null) {
            List<Long> userBookIdList = bookService.getAllBookIdByUserId(userId);
            log.info("Book id list for delete: {}", userBookIdList);

            userService.deleteUserById(userId);
            log.info("Delete user with id: {}", userId);

            if (userBookIdList != null && !userBookIdList.isEmpty()) {
                userBookIdList.stream()
                        .filter(Objects::nonNull)
                        .forEach(bookService::deleteBookById);
            }
        }

    }
}
