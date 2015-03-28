package webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RuralIvrsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuralIvrsApplication.class, args);
    }

}
