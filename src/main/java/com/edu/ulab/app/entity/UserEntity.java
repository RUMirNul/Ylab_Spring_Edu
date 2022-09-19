package com.edu.ulab.app.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class UserEntity extends BaseIdEntity<Long> {
    private Long id;
    private String fullName;
    private String title;
    private int age;

    public UserEntity() {
    }

    public UserEntity(Long id, String fullName, String title, int age) {
        this.id = id;
        this.fullName = fullName;
        this.title = title;
        this.age = age;
    }
}
