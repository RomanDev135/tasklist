package com.exampl.tasklist.repository;

import com.exampl.tasklist.domain.task.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TaskRepository {


    Optional<Task> findById(Long id);

    List<Task> findAllByUserId(Long userId);

    void assignToUserById(@Param("taskId") Long taskId, @Param("userId") Long userId); // связывает task с пользователем

    void update(Task task);

    void create(Task task);

    void delete(Long id);
}
