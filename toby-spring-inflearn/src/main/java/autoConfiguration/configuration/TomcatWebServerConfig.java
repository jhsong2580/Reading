package autoConfiguration.configuration;

import autoConfiguration.ConditionalMyOnClass;
import autoConfiguration.EnableMyConfigurationProperties;
import autoConfiguration.MyAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@MyAutoConfiguration
@ConditionalMyOnClass(value = "org.apache.catalina.startup.Tomcat")
//@EnableMyConfigurationProperties(ServerProperties.class)
public class TomcatWebServerConfig {

//    @Value("${context.path}") // PropertySourcesPlaceholderConfigurer.class 에 의해 properties 에 존재하는 값을 셋팅 할수 있다.
//    private String contextPath;
//
//    @Value("${context.portNotExist:9091}") // Default 값은 path:DEFAULT 로 한다
//    private int port;

//    @Bean("tomcatWebServerConfig")
//    @ConditionalOnMissingBean // ServletWebServerFactory가 Bean등록이 안되있다면 Bean등록
//    public ServletWebServerFactory servletWebServerFactory(Environment env) {
//        TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
////        tomcatServletWebServerFactory.setContextPath(env.getProperty("context.path")); // 모든 URI 앞에 app이 붙는다.
//        tomcatServletWebServerFactory.setContextPath(contextPath);
//        tomcatServletWebServerFactory.setPort(port);
//        return tomcatServletWebServerFactory;
//    }

    /* Properties 집합체를 통해 구현 */
    @Bean("tomcatWebServerConfig")
    @ConditionalOnMissingBean // ServletWebServerFactory가 Bean등록이 안되있다면 Bean등록
    public ServletWebServerFactory servletWebServerFactory(ServerProperties serverProperties) {
        TomcatServletWebServerFactory tomcatServletWebServerFactory = new TomcatServletWebServerFactory();
//        tomcatServletWebServerFactory.setContextPath(env.getProperty("context.path")); // 모든 URI 앞에 app이 붙는다.
        tomcatServletWebServerFactory.setContextPath(serverProperties.getPath());
        tomcatServletWebServerFactory.setPort(serverProperties.getPort());
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
