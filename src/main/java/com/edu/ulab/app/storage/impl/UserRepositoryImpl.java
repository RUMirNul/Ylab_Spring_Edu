package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.storage.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository<UserEntity, Long> {
    private static AtomicLong id;

    private final Map<Long, UserEntity> userIdAndUserEntityMap;

    public UserRepositoryImpl() {
        id = new AtomicLong(0);
        this.userIdAndUserEntityMap = new ConcurrentHashMap<>();
    }

    @Override
    public UserEntity createUser(UserEntity entity) {
        log.info("Creating user by entity: {}", entity);

        entity.setId(getNextId());
        userIdAndUserEntityMap.put(entity.getId(), entity);

        UserEntity copyStorageIndependentUserEntity = createStorageIndependentUserEntity(entity);
        log.info("Created user: {}", copyStorageIndependentUserEntity);

        return copyStorageIndependentUserEntity;
    }

    @Override
    public UserEntity updateUser(UserEntity entity) {
        log.info("Update user: {}", entity);

        Long id = entity.getId();
        log.info("User id for update: {}", id);

        if (id != null && userIdAndUserEntityMap.containsKey(id)) {
            UserEntity tempUserEntity = userIdAndUserEntityMap.get(id);
            tempUserEntity.setFullName(entity.getFullName());
            tempUserEntity.setTitle(entity.getTitle());
            tempUserEntity.setAge(entity.getAge());

            UserEntity copyStorageIndependentUserEntity = createStorageIndependentUserEntity(entity);
            log.info("Updated user: {}", copyStorageIndependentUserEntity);

            return copyStorageIndependentUserEntity;
        }

        return null;
    }

    @Override
    public UserEntity getUserById(Long id) {
        log.info("Got user by id: {}", id);

        if (userIdAndUserEntityMap.get(id) != null) {
            UserEntity copyStorageIndependentUserEntity = createStorageIndependentUserEntity(userIdAndUserEntityMap.get(id));
            log.info("Got user: {}", copyStorageIndependentUserEntity);

            return copyStorageIndependentUserEntity;
        }

        return null;
    }

    @Override
    public void deleteUserById(Long id) {
        log.info("Delete user by id: {}", id);

        if (id != null) {
            UserEntity deleteUser = userIdAndUserEntityMap.remove(id);
            log.info("Delete user: {}", deleteUser);
        }
    }

    private UserEntity createStorageIndependentUserEntity(UserEntity entity) {
        if (entity == null) return null;

        return UserEntity.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .title(entity.getTitle())
                .age(entity.getAge())
                .build();
    }

    private Long getNextId() {
        return id.getAndIncrement();
    }
}
