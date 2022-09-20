package com.edu.ulab.app.entity;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseIdEntity<Long> {
    @EqualsAndHashCode.Exclude
    private Long id;
    private String fullName;
    private String title;
    private int age;

}
