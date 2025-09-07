package io.github.m4gshm.spring.data.mock;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.AnnotationMetadata;

import java.util.stream.Stream;

import static io.github.m4gshm.spring.data.mock.RepositoryReplaceByMockPostProcessor.Properties;
import static io.github.m4gshm.spring.data.mock.RepositoryReplaceRegistrar.AnnotationAttributes.*;
import static org.springframework.beans.factory.config.BeanDefinition.ROLE_INFRASTRUCTURE;

public class RepositoryReplaceRegistrar implements ImportBeanDefinitionRegistrar {

    private static final String BEAN_NAME = RepositoryReplaceByMockPostProcessor.class.getSimpleName();

    private static boolean getBoolean(MergedAnnotation<EnableMockRepositories> mergedAnnotation, String property) {
        return Stream.of(mergedAnnotation)
                .filter(MergedAnnotation::isPresent)
                .anyMatch(a -> a.getBoolean(property));
    }

    @Override
    public void registerBeanDefinitions(@NonNull AnnotationMetadata importingClassMetadata,
                                        @NonNull BeanDefinitionRegistry registry) {
        if (!registry.containsBeanDefinition(BEAN_NAME)) {
            var annotations = importingClassMetadata.getAnnotations();
            var mergedAnnotation = annotations.get(EnableMockRepositories.class);
            registry.registerBeanDefinition(BEAN_NAME, BeanDefinitionBuilder
                    .rootBeanDefinition(RepositoryReplaceByMockPostProcessor.class)
                    .addPropertyValue(Properties.SOURCE.name, mergedAnnotation.getSource())
                    .addPropertyValue(Properties.RESETTABLE.name, getBoolean(mergedAnnotation, RESET_AFTER_TEST.name))
                    .addPropertyValue(Properties.REMOVE_REGISTRY.name, getBoolean(mergedAnnotation, REMOVE_REGISTRY.name))
                    .addPropertyValue(Properties.REMOVE_GENERATED_BY_REGISTRY.name, getBoolean(mergedAnnotation, REMOVE_GENERATED_BY_REGISTRY.name))
                    .setRole(ROLE_INFRASTRUCTURE)
                    .getBeanDefinition());
        }
    }

    @RequiredArgsConstructor
    public enum AnnotationAttributes {
        RESET_AFTER_TEST("resetAfterTest"),
        REMOVE_REGISTRY("removeSourceExtension"),
        REMOVE_GENERATED_BY_REGISTRY("removeGeneratedByExtension");

        public final String name;
    }

}
