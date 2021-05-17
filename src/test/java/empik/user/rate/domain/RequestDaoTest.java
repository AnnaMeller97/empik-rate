package empik.user.rate.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RequestDaoTest {

    @Autowired
    RequestDao requestDao;

    @Autowired
    EntityManager entityManager;

    @Test
    @Rollback
    @Transactional
    public void shouldAddNewLogin() {
        String login = "octocat";
        requestDao.logRequest(login);
        RequestCounter requestCounter = entityManager.find(RequestCounter.class, login);
        assertEquals(1, requestCounter.getRequestCount());
        assertEquals(login, requestCounter.getLogin());
    }

    @Test
    @Rollback
    @Transactional
    public void shouldIncrementRequestCount() {
        String login = "octocat";
        requestDao.logRequest(login);
        requestDao.logRequest(login);
        RequestCounter requestCounter = entityManager.find(RequestCounter.class, login);
        assertEquals(2, requestCounter.getRequestCount());
        assertEquals(login, requestCounter.getLogin());
    }
}
