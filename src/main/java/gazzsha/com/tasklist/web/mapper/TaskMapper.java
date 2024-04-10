package gazzsha.com.tasklist.web.mapper;


import gazzsha.com.tasklist.domain.task.Task;
import gazzsha.com.tasklist.web.dto.task.TaskDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper extends Mappable<Task, TaskDto> {
}
