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
        return userMapper.userEntityToUserDto(userDao.createUser(userMapper.userDtoToUserEntity(userDto)));
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        return userMapper.userEntityToUserDto(userDao.updateUser(userMapper.userDtoToUserEntity(userDto)));
    }

    @Override
    public UserDto getUserById(Long id) {
        return userMapper.userEntityToUserDto(userDao.getUserById(id));
    }

    @Override
    public void deleteUserById(Long id) {
        userDao.deleteUserById(id);
    }
}
