package com.example.exam.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
    NEW("Новый"),
        OK("Подтвержден"),
            NOOK("Отклонен");

    private final String status;
}
