package io.github.m4gshm.spring.data.mock;

import lombok.NonNull;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.repository.config.AnnotationRepositoryConfigurationSource;

import java.lang.annotation.Annotation;
import java.util.Optional;

import static java.util.Optional.empty;

class MockRepositoryConfigurationSource extends AnnotationRepositoryConfigurationSource {
    public MockRepositoryConfigurationSource(@NonNull AnnotationMetadata metadata,
                                             @NonNull Class<? extends Annotation> annotation,
                                             @NonNull ResourceLoader resourceLoader,
                                             @NonNull Environment environment,
                                             @NonNull BeanDefinitionRegistry registry,
                                             @NonNull BeanNameGenerator generator) {
        super(metadata, annotation, resourceLoader, environment, registry, generator);
    }

    @Override
    public Optional<String> getRepositoryImplementationPostfix() {
        return empty();
    }

    @Override
    public Optional<String> getNamedQueryLocation() {
        return empty();
    }

    @Override
    public Optional<String> getRepositoryFactoryBeanClassName() {
        return empty();
    }
}
