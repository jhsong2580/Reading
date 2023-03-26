package study.configuration;

import autoConfiguration.ConditionalMyOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class WebServerConfiguration {
//    @Bean
//    public ServletWebServerFactory customWebServerFactory() {
//        TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
//        serverFactory.setPort(9090);
//        return serverFactory;
//    }
}
