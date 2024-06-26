package gazzsha.com.tasklist.service.impl;

import gazzsha.com.tasklist.domain.exception.ResourceNotFoundException;
import gazzsha.com.tasklist.domain.task.Status;
import gazzsha.com.tasklist.domain.task.Task;
import gazzsha.com.tasklist.domain.user.User;
import gazzsha.com.tasklist.repository.jpa.TaskRepository;
import gazzsha.com.tasklist.service.TaskService;
import gazzsha.com.tasklist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "TaskService::getById", key = "#id")
    public Task getById(final Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Task with id %s not found.", id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllByUseId(final Long id) {
        return taskRepository.findAllByUserId(id);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @CachePut(value = "TaskService::getById", key = "#task.id")
    public Task update(final Task task) {
        if (Objects.isNull(task.getStatus())) {
            task.setStatus(Status.TODO);
        }
        taskRepository.save(task);
        return task;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Cacheable(value = "TaskService::getById", key = "#task.id")
    public Task create(final Task task, final Long userId) {
        User user = userService.getById(userId);
        task.setStatus(Status.TODO);
        user.getTasks().add(task);
        userService.update(user);
        return task;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @CacheEvict(value = "TaskService::getById", key = "#id")
    public void delete(final Long id) {
        taskRepository.deleteById(id);
    }
}
