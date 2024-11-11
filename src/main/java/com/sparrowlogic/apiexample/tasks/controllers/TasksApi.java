package com.sparrowlogic.apiexample.tasks.controllers;

import com.sparrowlogic.apiexample.tasks.requests.TaskRequest;
import com.sparrowlogic.apiexample.tasks.responses.TaskResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TasksApi {

    private final List<TaskResponse> taskResponseList = new ArrayList<>();

    @GetMapping("")
    List<TaskResponse> getAllTasks() {
        return this.taskResponseList;
    }

    @PostMapping("")
    List<TaskResponse> addTask(@Valid @RequestBody TaskRequest taskRequest) {
            taskResponseList.add(new TaskResponse(taskRequest.title(), taskRequest.description(), taskRequest.willingToPay().toString()));
        return taskResponseList; }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
