package empik.user.rate.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "request_counter")
@AllArgsConstructor
@NoArgsConstructor
public class RequestCounter {

    @Id
    @Column(name = "login")
    private String login;

    @Column(name = "request_count")
    private Long requestCount;
}
