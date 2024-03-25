package com.exampl.tasklist.web.controller;


import com.exampl.tasklist.domain.task.Task;
import com.exampl.tasklist.domain.user.User;
import com.exampl.tasklist.service.TaskService;
import com.exampl.tasklist.service.UserService;
import com.exampl.tasklist.web.dto.task.TaskDto;
import com.exampl.tasklist.web.dto.user.UserDto;
import com.exampl.tasklist.web.dto.validation.OnCreate;
import com.exampl.tasklist.web.dto.validation.OnUpdate;
import com.exampl.tasklist.web.mappers.TaskMapper;
import com.exampl.tasklist.web.mappers.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User controller", description = "User API")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    private final TaskService taskService;

    private final TaskMapper taskMapper;

    @PutMapping
    @Operation(summary = "Update user")
    public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User updateUser = userService.update(user);
        return userMapper.toDto(updateUser);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get UserDto by id")
    public UserDto getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by id")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}/tasks")
    @Operation(summary = "Get all user tasks")
    public List<TaskDto> getTasksByUserId(@PathVariable Long id) {
        List<Task> tasks = taskService.getAllByUserId(id);
        return taskMapper.toDto(tasks);
    }

    @PostMapping("/{id}/tasks")
    @Operation(summary = "Add task to user")
    public TaskDto createTask(@PathVariable Long id, @Validated(OnCreate.class)
    @RequestBody TaskDto dto) {
        Task task = taskMapper.toEntity(dto);
        Task createdTask = taskService.create(task, id);
        return taskMapper.toDto(createdTask);
    }


}
