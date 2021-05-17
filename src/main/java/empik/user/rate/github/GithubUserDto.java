package empik.user.rate.github;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
class GithubUserDto {
    private Integer id;
    private String login;
    private String name;
    private String type;
    private Integer followers;

    @JsonProperty(value = "avatar_url")
    private String avatarUrl;

    @JsonProperty(value = "created_at")
    private ZonedDateTime createdAt;

    @JsonProperty(value = "public_repos")
    private Integer publicRepositories;
}
