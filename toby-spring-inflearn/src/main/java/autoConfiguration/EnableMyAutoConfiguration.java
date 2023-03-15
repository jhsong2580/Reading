package autoConfiguration;

import autoConfiguration.configuration.DispatcherServletConfig;
import autoConfiguration.configuration.TomcatWebServerConfig;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({MyAutoConfigImportSelector.class}) // Class를 지정하면 해당 클래스를 불러온다
public @interface EnableMyAutoConfiguration {

}
