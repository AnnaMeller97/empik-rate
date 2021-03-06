package empik.user.rate;

import empik.user.rate.domain.UserDto;
import empik.user.rate.domain.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{login}")
    public UserDto getUser(@PathVariable("login") String login) {
        return userService.getUserRate(login);
    }
}
