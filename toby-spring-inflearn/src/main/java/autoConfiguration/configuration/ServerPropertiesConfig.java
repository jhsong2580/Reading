package autoConfiguration.configuration;

import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

public class ServerPropertiesConfig {

//    @Bean
//    public ServerProperties normalServerProperties(Environment env) {
//        ServerProperties serverProperties = new ServerProperties(
//            env.getProperty("context.path"),
//            Integer.valueOf((env.getProperty("context.port"))
//            )
//        );
//        return serverProperties;
//    }

//    @Bean /* Binder를 통한 주입. 필드에 properties와 이름이 똑같은 변수와 setter가 필요하다 */
//    public ServerProperties binderServerProperties(Environment env) {
//        return Binder.get(env)
//            .bind("context", ServerProperties.class)
//            .get();
//    }
}
