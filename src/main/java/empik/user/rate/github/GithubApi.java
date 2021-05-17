package empik.user.rate.github;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "github", url = "https://api.github.com")
interface GithubApi {

    @RequestMapping(method = RequestMethod.GET, value = "/users/{login}", produces = "application/json")
    GithubUserDto getUser(@PathVariable("login") String login);
}
