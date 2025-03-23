package io.github.m4gshm.spring.data.mock;

import lombok.NonNull;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import static org.springframework.beans.factory.config.BeanDefinition.ROLE_INFRASTRUCTURE;

public class RepositoryReplaceRegistrar implements ImportBeanDefinitionRegistrar {

    private static final String BEAN_NAME = RepositoryReplaceByMockPostProcessor.class.getSimpleName();

    @Override
    public void registerBeanDefinitions(@NonNull AnnotationMetadata importingClassMetadata,
                                        @NonNull BeanDefinitionRegistry registry) {
        if (!registry.containsBeanDefinition(BEAN_NAME)) {
            var replaceRepositoriesByMocksMergedAnnotation = importingClassMetadata.getAnnotations().get(ReplaceRepositoriesByMocks.class);
            boolean resetAfterTest = replaceRepositoriesByMocksMergedAnnotation.getBoolean("resetAfterTest");
            var definition = BeanDefinitionBuilder
                    .rootBeanDefinition(RepositoryReplaceByMockPostProcessor.class)
                    .addPropertyValue("resettable", resetAfterTest)
                    .getBeanDefinition();
            definition.setRole(ROLE_INFRASTRUCTURE);
            registry.registerBeanDefinition(BEAN_NAME, definition);
        }
    }


}
