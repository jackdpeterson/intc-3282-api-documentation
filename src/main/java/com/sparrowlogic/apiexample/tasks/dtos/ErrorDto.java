package com.sparrowlogic.apiexample.tasks.dtos;

public record ErrorDto(
        String objectName,
        String fieldName,
        String rejectedValue,
        String errorMessage
) {}