package study.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {


    @Override
    public String sayHello(String name) {
        return "Hello ! " + name;
    }

    class Test {
        String a;
    }
}
