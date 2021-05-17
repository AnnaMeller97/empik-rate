package empik.user.rate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EmpikRateApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmpikRateApplication.class, args);
    }

}

