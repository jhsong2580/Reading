package autoConfiguration.configuration;

import autoConfiguration.ConditionalMyOnClass;
import autoConfiguration.MyAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@MyAutoConfiguration
@ConditionalMyOnClass(value = "org.apache.catalina.startup.Tomcat")
public class TomcatWebServerConfig {

    @Bean("tomcatWebServerConfig")
    @ConditionalOnMissingBean // ServletWebServerFactory가 Bean등록이 안되있다면 Bean등록
    public ServletWebServerFactory servletWebServerFactory(Environment env) {
        TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
        tomcatServletWebServerFactory.setContextPath(env.getProperty("context.path")); // 모든 URI 앞에 app이 붙는다.
        return tomcatServletWebServerFactory;
    }
//
//    public static class TomcatCondition implements Condition {
//
//        @Override
//        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
//            /* org.apache.catalina.startup.Tomcat 이라는 Class가 등록되어있으면 동작! */
//            return ClassUtils.isPresent("org.apache.catalina.startup.Tomcat",
//                context.getClassLoader());
//        }
//    }
}
