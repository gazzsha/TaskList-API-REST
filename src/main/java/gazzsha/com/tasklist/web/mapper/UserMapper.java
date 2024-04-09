package gazzsha.com.tasklist.web.mapper;

import gazzsha.com.tasklist.domain.user.User;
import gazzsha.com.tasklist.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(UserDto dto);
}
