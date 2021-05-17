package empik.user.rate.domain;

import empik.user.rate.github.GithubGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final GithubGateway githubGateway;

    public UserDto getUserRate(String login) {
        return githubGateway.getGithubUser(login);
    }
}
