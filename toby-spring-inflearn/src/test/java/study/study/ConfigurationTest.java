package study.study;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class ConfigurationTest {
    @Test
    public void configuration (){
        //given
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(MyConfig.class);
        ac.refresh();

        //when
        Bean1 bean1 = ac.getBean(Bean1.class);
        Bean2 bean2 = ac.getBean(Bean2.class);

        //then
        assertThat(bean1.common).isEqualTo(bean2.common);
    }

    //Bean 1 <--- Common
    //Bean 2 <--- Common

    @Configuration
    static class MyConfig {
        @Bean
        Common getCommon() {
            return new Common();
        }

        @Bean
        Bean1 bean1() {
            return new Bean1(getCommon());
        }

        @Bean
        Bean2 bean2() {
            return new Bean2(getCommon());
        }
    }

    private static class Bean1 {
        private final Common common;

        public Bean1(Common common) {
            this.common = common;
        }
    }


    private static class Bean2 {
        private final Common common;

        public Bean2(Common common) {
            this.common = common;
        }
    }
    private static class Common {

    }

}
