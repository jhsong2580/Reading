package autoConfiguration.configuration;

import autoConfiguration.MyAutoConfiguration;
import autoConfiguration.MyConfigurationProperties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;

@MyAutoConfiguration
public class MyPropertyPostProcessorConfig {
    // MyConfigurationProperties가 부터있는 Bean에 대해서 Binder가 주입해준다.
    @Bean
    public BeanPostProcessor propertyPostProcessor(Environment env) {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName)
                throws BeansException {

                MyConfigurationProperties annotation = AnnotationUtils.findAnnotation(
                    bean.getClass(), MyConfigurationProperties.class);

                if(annotation == null)
                    return bean;

                return Binder.get(env).bindOrCreate(annotation.prefix(), bean.getClass());
            }
        };
    }
}
