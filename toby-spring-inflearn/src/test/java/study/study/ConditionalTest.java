package study.study;

import static org.assertj.core.api.Assertions.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class ConditionalTest {
    @Test
    public void conditional (){
        //given
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(Config1.class);
        ac.register(Config2.class);
        ac.refresh();

        //when
        MyBean bean = ac.getBean(MyBean.class);

        //then
        assertThat(bean).isNotNull();
    }

    @Test
    public void contextRunner (){

        ApplicationContextRunner contextRunner = new ApplicationContextRunner();
        contextRunner.withUserConfiguration(Config1.class)
            .run(context ->
                assertThat(context).hasSingleBean(MyBean.class)
            );

        contextRunner.withUserConfiguration(Config2.class)
            .run(context ->
                assertThat(context).doesNotHaveBean(MyBean.class));
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(BooleanConditional.class)
    @Configuration
    @interface BooleanConditionalAnnotation {
        boolean value() default true;
    }

    @Configuration
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(FalseConditional.class)
    static @interface FalseConditionalAnnotation {

    }
    @Configuration
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Conditional(TrueConditional.class)
    static @interface TrueConditionalAnnotation {

    }

//    @TrueConditionalAnnotation
    @BooleanConditionalAnnotation(value = true)
    public static class Config1 {
        @Bean
        MyBean myBean() {
            return new MyBean();
        }
    }


//    @FalseConditionalAnnotation
    @BooleanConditionalAnnotation(value = false)
    public static class Config2 {
        @Bean
        MyBean myBean() {
            return new MyBean();
        }
    }
    static class MyBean {

    }

    static class BooleanConditional implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(
                BooleanConditionalAnnotation.class.getName());
            return (boolean)annotationAttributes.get("value");
        }
    }

    static class TrueConditional implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return true;
        }
    }

    static class FalseConditional implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return false;
        }
    }
}
