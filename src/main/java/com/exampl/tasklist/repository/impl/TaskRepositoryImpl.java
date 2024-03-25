package com.exampl.tasklist.repository.impl;

import com.exampl.tasklist.domain.exception.ResourceMappingException;
import com.exampl.tasklist.domain.task.Task;
import com.exampl.tasklist.repository.DataSourceConfig;
import com.exampl.tasklist.repository.TaskRepository;
import com.exampl.tasklist.repository.mappers.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;


//@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = """
            select t.id              as task_id,
                   t.title           as task_title,
                   t.description     as task_description,
                   t.expiration_date as task_expiration_date,
                   t.status          as task_status
            from tasks t
            where t.    id = ?""";

    private final String FIND_ALL_BY_USER_ID = """
            select t.id              as task_id,
                   t.title           as task_title,
                   t.description     as task_description,
                   t.expiration_date as task_expiration_date,
                   t.status          as task_status
            from tasks t
                     join users_tasks ut on t.id = ut.task_id
            where ut.user_id = ?
            """;

    private final String ASSIGN = """
            insert into users_tasks (user_id, task_id)
            values (?, ?) """;

    private final String UPDATE = """
            update tasks
                title = ?,
                description = ?,
                expiration_date = ?,
                status = ?
            where id = ?""";

    private final String CREATE = """
            insert into tasks(title, description, expiration_date, status)
            values (?,?,?,?)""";

    private final String DELETE = """
            delete from tasks
            where id = ? """;

    @Override
    public Optional<Task> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID); //Выполнили sql запрос
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) { //Одна строка в ResultSet
                return Optional.ofNullable(TaskRowMapper.mapRow(rs)); //Передаём rs в метод чтобы получить task
            }


        } catch (SQLException e) {
            throw new ResourceMappingException("Error while finding user by id");
        }

    }

    @Override
    public List<Task> findAllByUserId(Long userId) {
        Connection connection = dataSourceConfig.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_USER_ID);
            statement.setLong(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                return TaskRowMapper.mapRows(rs);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while finding user by id");
        }
    }

    @Override
    public void assignToUserById(Long taskId, Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(ASSIGN);
            statement.setLong(1, taskId);
            statement.setLong(2, userId);
            statement.executeQuery();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while assiging to user");
        }
    }

    @Override
    public void update(Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, task.getTitle());
            if (task.getDescription() == null) {
                statement.setNull(2, Types.VARCHAR);
            } else {
                statement.setString(2, task.getDescription());
            }
            if (task.getExpirationData() == null) {
                statement.setNull(3, Types.TIMESTAMP);
            } else {
                statement.setTimestamp(3, Timestamp.valueOf(task.getExpirationData()));
            }
            statement.setString(4, task.getStatus().name());
            statement.setLong(5, task.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while updating to user");
        }
    }

    @Override
    public void create(Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setString(1, task.getTitle());
            if (task.getDescription() == null) {
                statement.setNull(2, Types.VARCHAR);
            } else {
                statement.setString(2, task.getDescription());
            }
            if (task.getExpirationData() == null) {
                statement.setNull(3, Types.VARCHAR);
            } else {
                statement.setTimestamp(3, Timestamp.valueOf(task.getExpirationData()));
            }
            statement.setString(4, task.getStatus().name());
            statement.executeUpdate();
            //Нужен ResultSet т.к. генерируются ключи и их надо добавить в Task при её создании
            try (ResultSet rs = statement.getGeneratedKeys()) {
                rs.next();
                task.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while creating to user");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("DELETE");
        }
    }

}
