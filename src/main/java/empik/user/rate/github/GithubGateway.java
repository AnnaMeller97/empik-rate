package empik.user.rate.github;

import empik.user.rate.domain.UserDto;
import empik.user.rate.exception.GithubException;
import empik.user.rate.exception.UserNotFound;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GithubGateway {

    private final GithubApi githubApi;
    private final UserMapper mapper;

    public UserDto getGithubUser(String login) {
        try {
            return mapper.mapToUserRateDto(githubApi.getUser(login));
        } catch (FeignException.NotFound e) {
            throw new UserNotFound();
        } catch (FeignException e) {
            throw new GithubException();
        }
    }
}
