package com.edu.ulab.app.entity;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookEntity extends BaseIdEntity<Long> {
    @EqualsAndHashCode.Exclude
    private Long id;
    @EqualsAndHashCode.Exclude
    private Long userId;
    private String title;
    private String author;
    private long pageCount;

}
