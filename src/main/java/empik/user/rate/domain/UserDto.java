package empik.user.rate.domain;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Optional;

@Data
public class UserDto {
    private Integer id;
    private String login;
    private String name;
    private String type;
    private String avatarUrl;
    private ZonedDateTime createdAt;
    private Optional<Double> calculations;
}
