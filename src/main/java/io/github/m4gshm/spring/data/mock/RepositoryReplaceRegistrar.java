package io.github.m4gshm.spring.data.mock;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import static org.springframework.beans.factory.config.BeanDefinition.ROLE_INFRASTRUCTURE;
import static org.springframework.util.Assert.notNull;

public class RepositoryReplaceRegistrar implements ImportBeanDefinitionRegistrar {

    private static final String BEAN_NAME = RepositoryReplaceByMockPostProcessor.class.getSimpleName();

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        notNull(registry, "Registry must not be null");
        if (!registry.containsBeanDefinition(BEAN_NAME)) {
            var definition = BeanDefinitionBuilder
                    .rootBeanDefinition(RepositoryReplaceByMockPostProcessor.class)
                    .getBeanDefinition();
            definition.setRole(ROLE_INFRASTRUCTURE);
            registry.registerBeanDefinition(BEAN_NAME, definition);
        }
    }
}
