package study.controller;

import java.util.Objects;
import javax.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.service.HelloService;


@RestController
public class HelloController {

    private final HelloService helloDecorator;

    public HelloController(HelloService helloDecorator) {
        this.helloDecorator = helloDecorator;
    }

    @PostConstruct
    public void init() {
        System.out.println(HelloController.class.getName() + " loaded");
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hello(String name) {
        return ResponseEntity.ok(helloDecorator.sayHello(Objects.requireNonNull(name)));
    }

}
