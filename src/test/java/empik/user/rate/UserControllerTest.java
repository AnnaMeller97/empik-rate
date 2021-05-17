package empik.user.rate;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import empik.user.rate.domain.RequestCounter;
import empik.user.rate.domain.UserDto;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManager;
import java.time.ZonedDateTime;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {WireMockInitializer.class})
public class UserControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void shouldReturnUserDto() {
        wireMockServer.stubFor(
                WireMock.get(WireMock.urlEqualTo("/users/octocat"))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.SC_OK)
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("responses/validResponse.json"))
        );
        UserDto as = RestAssured.given()
                .when()
                .get("http://localhost:" + port + "/users/octocat")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(UserDto.class);

        assertEquals(583231, as.getId());
        assertEquals("octocat", as.getLogin());
        assertEquals("The Octocat", as.getName());
        assertEquals("User", as.getType());
        assertEquals("https://avatars.githubusercontent.com/u/583231?v=4", as.getAvatarUrl());
        assertEquals(ZonedDateTime.parse("2011-01-25T18:44:36Z").toInstant(), as.getCreatedAt().toInstant());
        assertEquals(Optional.of(0.016042780748663103), as.getCalculations());

        RequestCounter octocat = entityManager.find(RequestCounter.class, "octocat");
        assertEquals(1, octocat.getRequestCount());
    }

    @Test
    public void shouldReturnUserDtoWithEmptyCalculations() {
        wireMockServer.stubFor(
                WireMock.get(WireMock.urlEqualTo("/users/octocat"))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.SC_OK)
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("responses/validResponseWithZeroFollowers.json"))
        );
        UserDto as = RestAssured.given()
                .when()
                .get("http://localhost:" + port + "/users/octocat")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().body().as(UserDto.class);

        assertEquals(583231, as.getId());
        assertEquals("octocat", as.getLogin());
        assertEquals("The Octocat", as.getName());
        assertEquals("User", as.getType());
        assertEquals("https://avatars.githubusercontent.com/u/583231?v=4", as.getAvatarUrl());
        assertEquals(ZonedDateTime.parse("2011-01-25T18:44:36Z").toInstant(), as.getCreatedAt().toInstant());
        assertEquals(Optional.empty(), as.getCalculations());

        RequestCounter octocat = entityManager.find(RequestCounter.class, "octocat");
        assertEquals(1, octocat.getRequestCount());
    }

    @Test
    public void shouldReturn404() {
        wireMockServer.stubFor(
                WireMock.get(WireMock.urlEqualTo("/users/octocat"))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.SC_NOT_FOUND)
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("responses/notFound.json"))
        );
        RestAssured.given()
                .when()
                .get("http://localhost:" + port + "/users/octocat")
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);

        RequestCounter octocat = entityManager.find(RequestCounter.class, "octocat");
        assertEquals(1, octocat.getRequestCount());
    }

    @Test
    public void shouldReturn502() {
        wireMockServer.stubFor(
                WireMock.get(WireMock.urlEqualTo("/users/octocat"))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR))
        );
        RestAssured.given()
                .when()
                .get("http://localhost:" + port + "/users/octocat")
                .then()
                .statusCode(HttpStatus.SC_BAD_GATEWAY);
        RequestCounter octocat = entityManager.find(RequestCounter.class, "octocat");
        assertEquals(1, octocat.getRequestCount());
    }

    @Test
    public void shouldCountRequests() {
        wireMockServer.stubFor(
                WireMock.get(WireMock.urlEqualTo("/users/octocat"))
                        .willReturn(aResponse()
                                .withStatus(HttpStatus.SC_OK)
                                .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("responses/validResponse.json"))
        );
        RestAssured.given()
                .when()
                .get("http://localhost:" + port + "/users/octocat")
                .then()
                .statusCode(HttpStatus.SC_OK);
        RestAssured.given()
                .when()
                .get("http://localhost:" + port + "/users/octocat")
                .then()
                .statusCode(HttpStatus.SC_OK);

        RequestCounter octocat = entityManager.find(RequestCounter.class, "octocat");
        assertEquals(2, octocat.getRequestCount());
    }
}