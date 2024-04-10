package gazzsha.com.tasklist.web.mapper;

import gazzsha.com.tasklist.domain.user.User;
import gazzsha.com.tasklist.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto> {
}
