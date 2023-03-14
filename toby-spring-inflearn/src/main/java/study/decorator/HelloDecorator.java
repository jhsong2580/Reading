package study.decorator;

import org.springframework.stereotype.Service;
import study.service.HelloService;

@Service
public class HelloDecorator implements HelloService {

    private final HelloService helloServiceImpl;

    public HelloDecorator(HelloService helloServiceImpl) {
        this.helloServiceImpl = helloServiceImpl;
    }

    @Override
    public String sayHello(String name) {
        return "*" + helloServiceImpl.sayHello(name) + "*";
    }

}
