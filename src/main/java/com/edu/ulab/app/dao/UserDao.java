package com.edu.ulab.app.dao;

public interface UserDao<E,K> {

    E createUser(E entity);
    E updateUser(E entity);
    E getUserById(K id);
    void deleteUserById(K id);
}
