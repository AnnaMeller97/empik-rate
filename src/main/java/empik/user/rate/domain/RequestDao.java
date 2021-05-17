package empik.user.rate.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class RequestDao {

    private final EntityManager entityManager;

    @Transactional
    public void logRequest(String login) {
        entityManager.createNativeQuery("insert into request_counter (login, request_count) " +
                "values (:login, :request_count) " +
                "on duplicate key update " +
                "request_count = request_count + 1")
                .setParameter("login", login)
                .setParameter("request_count", 1)
                .executeUpdate();
    }
}
