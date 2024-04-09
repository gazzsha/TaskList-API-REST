package gazzsha.com.tasklist.service;

import gazzsha.com.tasklist.domain.task.Task;

import java.util.List;

public interface TaskService {

    Task getById(Long id);

    List<Task> getAllByUseId(Long id);

    Task update(Task task);

    Task create(Task task,Long userId);


    void delete(Long id);

}
