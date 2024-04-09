package gazzsha.com.tasklist.web.dto.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import gazzsha.com.tasklist.web.dto.validation.OnCreate;
import gazzsha.com.tasklist.web.dto.validation.OnUpdate;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Schema(description = "User DTO")
public class UserDto {

    @Schema(description = "User Id", example = "1")
    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;

    @Schema(description = "User name", example = "Aydar Rysaev")
    @NotNull(message = "Name must be not null.", groups = {OnUpdate.class, OnUpdate.class})
    @Length(max = 255, message = "Name length must be less than 255 symbols", groups = {OnUpdate.class, OnCreate.class})
    private String name;
    @Schema(description = "User email", example = "rysaev@mail.ru")
    @NotNull(message = "Username must be not null.", groups = {OnUpdate.class, OnUpdate.class})
    @Length(max = 255, message = "Username length must be less than 255 symbols", groups = {OnUpdate.class, OnCreate.class})
    private String username;

    @Schema(description = "User crypted password", example = "$2a$10$Xw8XGdbt5ce812XXv484se3iFCmvHrHTqszIQSLFOOFkbNPqCuCsm")
    @NotNull(message = "Password must be not null", groups = {OnCreate.class, OnUpdate.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


    @Schema(name = "User password confirmation", example = "$2a$10$Xw8XGdbt5ce812XXv484se3iFCmvHrHTqszIQSLFOOFkbNPqCuCsm")
    @NotNull(message = "Password confirmation must be not null", groups = {OnCreate.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordConfirmation;
}
