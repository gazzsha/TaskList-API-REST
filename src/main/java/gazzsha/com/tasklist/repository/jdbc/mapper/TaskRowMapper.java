package gazzsha.com.tasklist.repository.jdbc.mapper;

import gazzsha.com.tasklist.domain.task.Status;
import gazzsha.com.tasklist.domain.task.Task;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TaskRowMapper {


    @SneakyThrows
    public static Task mapRow(final ResultSet resultSet) {
        if (resultSet.next()) {
            Task task = new Task();
            task.setId(resultSet.getLong("task_id"));
            task.setTitle(resultSet.getString("task_title"));
            task.setDescription(resultSet.getString("task_description"));
            task.setStatus(Status.valueOf(resultSet.getString("task_status")));
            Timestamp timestamp = resultSet.getTimestamp("task_expiration_date");
            if (Objects.nonNull(timestamp)) {
                task.setExpirationDate(timestamp.toLocalDateTime());
            }
            return task;
        }
        return null;
    }


    @SneakyThrows
    public static List<Task> mapRows(final ResultSet resultSet) {
        List<Task> tasks = new ArrayList<>(resultSet.getRow());
        while (resultSet.next()) {
            Task task = new Task();
            task.setId(resultSet.getLong("task_id"));
            if (!resultSet.wasNull()) {
                task.setTitle(resultSet.getString("task_title"));
                task.setDescription(resultSet.getString("task_description"));
                task.setStatus(Status.valueOf(resultSet.getString("task_status")));
                Timestamp timestamp = resultSet.getTimestamp("task_expiration_date");
                if (Objects.nonNull(timestamp)) {
                    task.setExpirationDate(timestamp.toLocalDateTime());
                }
            }
            tasks.add(task);
        }
        return tasks;
    }
}
