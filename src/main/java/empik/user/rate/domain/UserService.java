package empik.user.rate.domain;

import empik.user.rate.github.GithubGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final GithubGateway githubApi;
    private final RequestDao requestDao;

    public UserDto getUserRate(String login) {
        requestDao.logRequest(login);
        return githubApi.getGithubUser(login);
    }
}
