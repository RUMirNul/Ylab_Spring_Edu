package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.BaseIdEntity;

public interface UserRepository<E extends BaseIdEntity, K extends Number> {

    E createUser(E entity);
    E updateUser(E entity);
    E getUserById(K id);
    void deleteUserById(K id);
}
