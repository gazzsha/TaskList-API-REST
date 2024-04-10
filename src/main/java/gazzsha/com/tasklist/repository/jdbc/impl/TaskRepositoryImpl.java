package gazzsha.com.tasklist.repository.jdbc.impl;

import gazzsha.com.tasklist.domain.exception.ResourceMappingException;
import gazzsha.com.tasklist.domain.task.Task;
import gazzsha.com.tasklist.repository.jdbc.DataSourceConfig;
import gazzsha.com.tasklist.repository.jdbc.TaskRepository;
import gazzsha.com.tasklist.repository.jdbc.mapper.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = """
            SELECT t.id AS task_id,
                   t.title AS task_title,
                   t.description AS task_description,
                   t.expiration_date AS task_expiration_date,
                   t.status AS task_status
            FROM tasks t
            WHERE t.id = ?
            """;

    private final String FIND_ALL_BY_USER_ID = """
            SELECT t.id AS task_id,
                   t.title AS task_title,
                   t.description AS task_description,
                   t.expiration_date AS task_expiration_date,
                   t.status AS task_status
            FROM tasks t
                JOIN users_tasks ut ON t.id = ut.task_id
            WHERE ut.user_id = ?
            """;

    private final String ASSIGN = """
            INSERT INTO users_tasks(user_id, task_id)
            VALUES (?,?)
            """;

    private final String DELETE = """
            DELETE FROM tasks
            WHERE id = ?
            """;

    private final String UPDATE = """
            UPDATE tasks SET title = ?,
                             description = ?,
                             expiration_date = ?,
                             status = ?
            WHERE id = ?
            """;
    private final String CREATE = """
            INSERT INTO tasks(title, description,expiration_date,status)
            VALUES (?,?,?,?)
            """;

    @Override
    public Optional<Task> findById(final Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return Optional.ofNullable(TaskRowMapper.mapRow(resultSet));
            }
        } catch (final SQLException e) {
            throw new ResourceMappingException(String.format("Error while finding by id %s", id));
        }
    }

    @Override
    public List<Task> findAllByUserId(final Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_USER_ID);
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                return TaskRowMapper.mapRows(resultSet);
            }
        } catch (final SQLException e) {
            throw new ResourceMappingException(String.format("Error while finding all by userId %s", userId));
        }
    }

    @Override
    public void assignToUserById(final Long taskId, final Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(ASSIGN);
            statement.setLong(1, userId);
            statement.setLong(2, taskId);
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new ResourceMappingException(String.format("Error while assign to userId %s task with id %s", userId, taskId));
        }
    }

    @Override
    public void update(final Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE);
            statement.setString(1, task.getTitle());
            if (Objects.isNull(task.getDescription())) {
                statement.setNull(2, Types.VARCHAR);
            } else {
                statement.setString(2, task.getDescription());
            }
            if (Objects.isNull(task.getExpirationDate())) {
                statement.setNull(3, Types.TIMESTAMP);
            } else {
                statement.setTimestamp(3, Timestamp.valueOf(task.getExpirationDate()));
            }
            statement.setString(4, task.getStatus().name());
            statement.setLong(5, task.getId());
            statement.executeUpdate();
        } catch (final SQLException e) {
            throw new ResourceMappingException(String.format("Error while updating task with id %s", task.getId()));
        }
    }

    @Override
    public void create(final Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, task.getTitle());
            if (Objects.isNull(task.getDescription())) {
                statement.setNull(2, Types.VARCHAR);
            } else {
                statement.setString(2, task.getDescription());
            }
            if (Objects.isNull(task.getExpirationDate())) {
                statement.setNull(3, Types.TIMESTAMP);
            } else {
                statement.setTimestamp(3, Timestamp.valueOf(task.getExpirationDate()));
            }
            statement.setString(4, task.getStatus().name());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                task.setId(resultSet.getLong((1)));
            }
        } catch (final SQLException e) {
            throw new ResourceMappingException("Error while creating task");
        }
    }

    @Override
    public void delete(final Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException(String.format("Error while deleting task with id %s", id));
        }
    }
}
