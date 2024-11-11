package com.sparrowlogic.apiexample.tasks.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparrowlogic.apiexample.tasks.requests.TaskRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;


@WebMvcTest
@AutoConfigureMockMvc
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs(outputDir = "target/snippets")
class TasksApiTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void createTasksProvides200OkWithValidTaskRequest() throws Exception {
        TaskRequest taskRequest = new TaskRequest("title", "description", 5);
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(taskRequest);

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.post("/api/tasks")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk());

        resultActions.andDo(document("tasks/create",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        ));
    }

    @Test
    public void createTaskThrows4xxWithAmountTooLow() throws Exception {
        TaskRequest taskRequest = new TaskRequest("title", "description", 4);
        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(taskRequest);

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.post("/api/tasks")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        resultActions.andDo(document("create-tasks-invalid",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        ));
    }
}
