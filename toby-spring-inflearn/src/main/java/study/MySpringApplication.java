package study;

import org.springframework.boot.SpringApplication;
import study.annotation.MySpringBootApplication;

@MySpringBootApplication
public class MySpringApplication {



    public static void main(String[] args) {
        SpringApplication.run(MySpringApplication.class, args);
        System.out.println("A");
    }
}
