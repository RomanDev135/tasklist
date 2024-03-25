package com.exampl.tasklist.web.controller;

import com.exampl.tasklist.domain.task.Task;
import com.exampl.tasklist.service.TaskService;
import com.exampl.tasklist.web.dto.task.TaskDto;
import com.exampl.tasklist.web.dto.validation.OnUpdate;
import com.exampl.tasklist.web.mappers.TaskMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Validated
@Tag(name = "Task controller", description = "Task API")
public class TaskController {

    private final TaskService taskService;

    private final TaskMapper taskMapper;


    @GetMapping("/{id}")
    @Operation(summary = "Get taskDto by id")
    public TaskDto getById(@PathVariable Long id) {
        Task task = taskService.getById(id);
        return taskMapper.toDto(task);

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task by id")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }

    @PutMapping
    @Operation(summary = "Update taskDto")
    public TaskDto update(@Validated(OnUpdate.class) @RequestBody TaskDto dto) {
        Task task = taskMapper.toEntity(dto); //Преобразовал в сущность
        Task updatedtask = taskService.update(task);
        return taskMapper.toDto(updatedtask);//Преобразовал в dto и вернул
    }

}
