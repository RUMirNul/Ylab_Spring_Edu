package com.edu.ulab.app.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class BookEntity extends BaseIdEntity<Long> {
    private Long id;
    private Long userId;
    private String title;
    private String author;
    private long pageCount;

    public BookEntity() {
    }

    public BookEntity(Long id, Long userId, String title, String author, long pageCount) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.author = author;
        this.pageCount = pageCount;
    }
}
