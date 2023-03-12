package study.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.controller.HelloController;
import study.service.HelloService;
import study.service.HelloServiceImpl;

/**
 * Bean을 등록하기위한 방법중 하나.
     * @Configuration이 붙은 Class 하위의 @Bean 들을 @Bean으로 등록한다.
 * 단 @Configuration도 @Component가 있다. Component Scan에 의해 이 Class가 Scan을 해야한다.
 * 다른 테스트를 위해 @Configuration을 주석처리 해둔다!
*/
//@Configuration
public class MyBeanFactory {

    @Bean
    public HelloController helloController(HelloService helloService) {
        return new HelloController(helloService);
    }
    @Bean
    public HelloService helloService() {
        return new HelloServiceImpl();
    }

}
