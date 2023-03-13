package study.controller;

import java.util.Objects;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import study.annotation.MyComponent;
import study.service.HelloService;


@RestController
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @PostConstruct
    public void init() {
        System.out.println(HelloController.class.getName() + " loaded");
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hello(String name) {
        return ResponseEntity.ok(helloService.sayHello(Objects.requireNonNull(name)));
    }

}
