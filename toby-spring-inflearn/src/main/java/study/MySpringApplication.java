package study;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import study.annotation.MySpringBootApplication;

@MySpringBootApplication
public class MySpringApplication {

    /* ApplicationRunner로 등록한 Bean은 환경 셋팅이 끝난 후에 한꺼번에 실행된다.  */
    @Bean
    ApplicationRunner applicationRunner1(Environment env) { // Environment : Spring환경 변수들을 추상화 해놓음
        return (args) -> {
            String name = env.getProperty("my.name");
            System.out.println("my.name1 : " + name);
        };
    }

    @Bean
    ApplicationRunner applicationRunner2(Environment env) { // Environment : Spring환경 변수들을 추상화 해놓음
        return (args) -> {
            String name = env.getProperty("my.name");
            System.out.println("my.name2 : " + name);
        };
    }
    public static void main(String[] args) {
        SpringApplication.run(MySpringApplication.class, args);
        System.out.println("A");
    }
}
