package study;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@SpringBootApplication
@Slf4j
public class TestApplication extends WebMvcConfigurationSupport {


    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class);
    }

    @Bean
    public RestTemplate generalRestTemplate() {

        RestTemplate restTemplate = new RestTemplate();
        log.info("test");
        return restTemplate;
    }

}
