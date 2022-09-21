package com.edu.ulab.app.dao.impl;

import com.edu.ulab.app.dao.UserDao;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.storage.UserStorage;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao<UserEntity, Long> {

    private final UserStorage<UserEntity, Long> storage;

    public UserDaoImpl(UserStorage<UserEntity, Long> storage) {
        this.storage = storage;
    }

    @Override
    public UserEntity createUser(UserEntity entity) {
        return storage.createUser(entity);
    }

    @Override
    public UserEntity updateUser(UserEntity entity) {
        return storage.updateUser(entity);
    }

    @Override
    public UserEntity getUserById(Long id) {
        return storage.getUserById(id);
    }

    @Override
    public void deleteUserById(Long id) {
        storage.deleteUserById(id);
    }
}
