package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dao.UserDao;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserDao<UserEntity, Long> userDao;
    private final UserMapper userMapper;

    public UserServiceImpl(UserDao<UserEntity, Long> userDao, UserMapper userMapper) {
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("Got create user by user DTO: {}", userDto);

        UserDto createdUser = userMapper.userEntityToUserDto(userDao.createUser(userMapper.userDtoToUserEntity(userDto)));
        log.info("Created user: {}", createdUser);

        return createdUser;
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        log.info("Got update user by user DTO: {}", userDto);

        UserDto updatedUser = userMapper.userEntityToUserDto(userDao.updateUser(userMapper.userDtoToUserEntity(userDto)));
        log.info("Updated user: {}", updatedUser);

        return updatedUser;
    }

    @Override
    public UserDto getUserById(Long id) {
        log.info("Wants get user by user id: {}", id);

        UserDto receivedUser = userMapper.userEntityToUserDto(userDao.getUserById(id));
        log.info("Received user: {}", receivedUser);

        return receivedUser;
    }

    @Override
    public void deleteUserById(Long id) {
        log.info("Got delete user by user id: {}", id);

        userDao.deleteUserById(id);
    }
}
