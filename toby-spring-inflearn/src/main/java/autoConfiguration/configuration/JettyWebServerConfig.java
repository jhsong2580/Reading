package autoConfiguration.configuration;

import autoConfiguration.ConditionalMyOnClass;
import autoConfiguration.MyAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@MyAutoConfiguration
@ConditionalMyOnClass(value = "org.eclipse.jetty.server.Server")
public class JettyWebServerConfig {

    @Bean("jettyWebServerFactory")
    @ConditionalOnMissingBean // ServletWebServerFactory가 Bean등록이 안되있다면 Bean등록
    public ServletWebServerFactory servletWebServerFactory() {
        return new JettyServletWebServerFactory();
    }

//    public static class JettyCondition implements Condition {
//
//        @Override
//        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
//            /* org.apache.catalina.startup.Tomcat 이라는 Class가 Bean으로 등록되어있으면 동작! */
//            return ClassUtils.isPresent("org.eclipse.jetty.server.Server",
//                context.getClassLoader());
//        }
//    }
}
