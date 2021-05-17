package empik.user.rate.github;

import empik.user.rate.domain.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Optional;

@Mapper
interface UserMapper {

    @Mapping(source = "githubUserDto", target = "calculations", qualifiedByName = "calculate")
    UserDto mapToUserRateDto(GithubUserDto githubUserDto);

    @Named("calculate")
    default Optional<Double> calculate(GithubUserDto githubUserDto) {
        if (githubUserDto.getFollowers() == 0) {
            return Optional.empty();
        }
        return Optional.of(6.0 / githubUserDto.getFollowers() * (2 + githubUserDto.getPublicRepositories()));
    }
}

