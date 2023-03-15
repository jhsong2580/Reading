package autoConfiguration;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.parsing.ImportDefinition;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.context.index.CandidateComponentsIndex;
import org.springframework.core.type.AnnotationMetadata;

public class MyAutoConfigImportSelector implements DeferredImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        return new String[]{
            "autoConfiguration/configuration/DispatcherServletConfig",
            "autoConfiguration/configuration/TomcatWebServerConfig",
            "autoConfiguration/configuration/JettyWebServerConfig",
        };
    }
}
