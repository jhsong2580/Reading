package autoConfiguration;

import autoConfiguration.ConditionalMyOnClass;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.ClassUtils;

public class MyOnClassCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String className = (String)metadata.getAnnotationAttributes(ConditionalMyOnClass.class.getName())
            .get("value");
        return ClassUtils.isPresent(className, context.getClassLoader()); /* 이 클래스가 존재하는가? */
    }
}
