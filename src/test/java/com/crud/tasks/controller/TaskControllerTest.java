package com.crud.tasks.controller;

import com.crud.tasks.domain.Dto.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;

import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskMapper taskMapper;

    @MockBean
    private DbService service;

    @Test
    void shouldFetchEmptyTasks() throws Exception {
        //Given
        when(taskMapper.mapToTaskDtoList(service.getAllTasks())).thenReturn(List.of());

        //When & Then
        mockMvc.
                perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchTasks() throws Exception {
        //Given
        List<TaskDto> tasks = new ArrayList<>();
        tasks.add(new TaskDto(1L, "Test title", "Test content"));
        tasks.add(new TaskDto(2L, "Test Title", "Test Content"));

        when(taskMapper.mapToTaskDtoList(service.getAllTasks())).thenReturn(tasks);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is("Test title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].content", Matchers.is("Test content")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title", Matchers.is("Test Title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].content", Matchers.is("Test Content")));
    }

    @Test
    void shouldFetchTask() throws Exception {
        //Given
        List<TaskDto> tasks = new ArrayList<>();
        tasks.add(new TaskDto(1L, "Test title", "Test content"));
        tasks.add(new TaskDto(2L, "Test Title", "Test Content"));

        when(taskMapper.mapToTaskDto(service.getTask(2L))).thenReturn(tasks.get(1));

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Test Title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Test Content")));
    }

    @Test
    void shouldDeleteTask() throws Exception {
        //Given
        List<TaskDto> tasks = new ArrayList<>();
        tasks.add(new TaskDto(1L, "Test title", "Test content"));
        tasks.add(new TaskDto(2L, "Test Title", "Test Content"));

        when(taskMapper.mapToTaskDto(service.deleteTask(taskMapper.mapToTask(tasks.get(1))))).thenReturn(tasks.get(1));
        Gson gson = new Gson();

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gson.toJson(tasks.get(1))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldUpdateTask() throws Exception {
        //Given

        TaskDto taskDto = new TaskDto(1L, "Test title", "Test content");


        TaskDto taskDtos = new TaskDto(1L, "Updated title", "Updated content");

        when(taskMapper.mapToTaskDto(service.saveTask(taskMapper.mapToTask(taskDto)))).thenReturn(taskDtos);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDtos);
        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is("Updated title")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.is("Updated content")));
    }

    @Test
    void shouldCreateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(1L, "Test title", "Test content");

        service.saveTask(taskMapper.mapToTask(taskDto));
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}