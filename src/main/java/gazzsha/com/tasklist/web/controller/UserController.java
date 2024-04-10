package gazzsha.com.tasklist.web.controller;


import gazzsha.com.tasklist.domain.task.Task;
import gazzsha.com.tasklist.domain.user.User;
import gazzsha.com.tasklist.service.TaskService;
import gazzsha.com.tasklist.service.UserService;
import gazzsha.com.tasklist.web.dto.task.TaskDto;
import gazzsha.com.tasklist.web.dto.user.UserDto;
import gazzsha.com.tasklist.web.dto.validation.OnCreate;
import gazzsha.com.tasklist.web.dto.validation.OnUpdate;
import gazzsha.com.tasklist.web.mapper.TaskMapper;
import gazzsha.com.tasklist.web.mapper.UserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User Controller", description = "User API")
public class UserController {

    private final UserService userService;
    private final TaskService taskService;
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    @PutMapping
    @Operation(summary = "Update user")
    @PreAuthorize(value = "@customSecurityExpression.canAccessUser(#dto.id)")
    public UserDto update(@Validated(OnUpdate.class) @RequestBody final UserDto dto) {
        User user = userMapper.toEntity(dto);
        User updatedUser = userService.update(user);
        return userMapper.toDto(updatedUser);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get userDto by id")
    @PreAuthorize(value = "@customSecurityExpression.canAccessUser(#id)")
    public UserDto getById(@PathVariable(name = "id") final Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }


    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete user by id")
    @PreAuthorize(value = "@customSecurityExpression.canAccessUser(#id)")
    public void deleteById(@PathVariable(name = "id") final Long id) {
        userService.delete(id);
    }

    @GetMapping(value = "/{id}/tasks")
    @Operation(summary = "Get all user tasks")
    @PreAuthorize(value = "@customSecurityExpression.canAccessUser(#id)")
    public List<TaskDto> getTasksByUserId(@PathVariable(name = "id") final Long id) {
        final List<Task> tasks = taskService.getAllByUseId(id);
        return taskMapper.toDto(tasks);
    }

    @PostMapping(value = "/{id}/tasks")
    @Operation(summary = "Add task to user")
    @PreAuthorize(value = "@customSecurityExpression.canAccessUser(#id)")
    public TaskDto createTask(@PathVariable(name = "id") final Long id, @Validated(OnCreate.class) @RequestBody final TaskDto dto) {
        Task task = taskMapper.toEntity(dto);
        Task createdTask = taskService.create(task, id);
        return taskMapper.toDto(createdTask);
    }
}
