package empik.user.rate.github;

import empik.user.rate.domain.UserDto;
import net.bytebuddy.dynamic.DynamicType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void shouldMapUser() {
        GithubUserDto input = prepareGithubUserDto(4, 6);
        UserDto result = userMapper.mapToUserRateDto(input);
        assertEquals(input.getId(), result.getId());
        assertEquals(input.getLogin(), result.getLogin());
        assertEquals(input.getName(), result.getName());
        assertEquals(input.getType(), result.getType());
        assertEquals(input.getAvatarUrl(), result.getAvatarUrl());
        assertEquals(input.getCreatedAt(), result.getCreatedAt());
        assertTrue(result.getCalculations().isPresent());
        assertEquals(12.0, result.getCalculations().get());
    }

    @ParameterizedTest
    @MethodSource("setCalculationsParameters")
    public void shouldSetCalculations(Optional<Double> expected, Integer followers, Integer publicRepositories) {
        GithubUserDto input = prepareGithubUserDto(followers, publicRepositories);
        UserDto result = userMapper.mapToUserRateDto(input);
        assertEquals(expected, result.getCalculations());
    }

    private static Stream<Arguments> setCalculationsParameters() {
        return Stream.of(
                Arguments.of(Optional.empty(), 0, 0),
                Arguments.of(Optional.empty(), 0, 8),
                Arguments.of(Optional.of(0.0182370820668693), 658, 0),
                Arguments.of(Optional.of(18.0), -1, -5),
                Arguments.of(Optional.of(0.0), 2, -2),
                Arguments.of(Optional.of(0.43239803044467706), 2147896, 154789),
                Arguments.of(Optional.of(-6.0), Integer.MAX_VALUE, Integer.MAX_VALUE),
                Arguments.of(Optional.of(-2.5769803764E9), 5, Integer.MAX_VALUE),
                Arguments.of(Optional.of(1.9557774076032347E-8), Integer.MAX_VALUE, 5)
        );
    }

    private GithubUserDto prepareGithubUserDto(Integer followers, Integer publicRepositories) {
        return new GithubUserDto(1,
                "userLogin",
                "username",
                "User",
                followers,
                "https://avatars.githubusercontent.com/u/583231?v=4",
                ZonedDateTime.now(),
                publicRepositories);
    }
}
