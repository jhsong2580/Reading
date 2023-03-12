package study.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Hello ! " + name;
    }
}
