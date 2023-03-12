package study;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import study.configuration.MyBeanFactory;
import study.controller.HelloController;
import study.service.HelloService;
import study.service.HelloServiceImpl;


public class TobyApplication {
//
//    @Bean
//    public ServletWebServerFactory servletWebServerFactory() {
//        return new TomcatServletWebServerFactory();
//    }
//
//    @Bean
//    public DispatcherServlet dispatcherServlet() {
//        return new DispatcherServlet() {
//            @Override
//            public void setApplicationContext(ApplicationContext applicationContext) {
//                super.setApplicationContext(applicationContext);
//                System.out.println(applicationContext);
//            }
//        };
//    }
//
//
//    private static AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
//        @Override
//        protected void onRefresh() {
//            super.onRefresh();
//            ServletWebServerFactory tomcatServletWebServerFactory = this.getBean(ServletWebServerFactory.class);
//            DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);
////            dispatcherServlet.setApplicationContext(this); // 이 코드가 없어도, ApplicationContext Setter에 의해 Setter 주입이 일어난다.
//
//            WebServer webServer = tomcatServletWebServerFactory.getWebServer(
//                servletContext -> servletContext.addServlet("dispatcherServlet", dispatcherServlet)
//                    .addMapping("/*")
//            );// servlet container를 만드는 실직적 함수
//            webServer.start();
//        }
//    };
//
//    public static void main(String[] args) {
//        applicationContext.register(TobyApplication.class);
//
//        applicationContext.refresh();
//
//    }
//
//    private static ServletContextInitializer getDispatcherServletInfo(WebApplicationContext applicationContext) {
//        ServletContextInitializer servletContextInitializer =
//            (servletContext) -> {
//                servletContext.addServlet("dispatcherServlet",
//                    new DispatcherServlet(applicationContext)
//                ).addMapping("/*");
//            };
//
//        return servletContextInitializer;
//    }

}
