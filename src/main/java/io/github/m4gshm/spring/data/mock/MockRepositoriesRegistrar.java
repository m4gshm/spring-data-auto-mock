package io.github.m4gshm.spring.data.mock;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.repository.config.AnnotationRepositoryConfigurationSource;
import org.springframework.data.repository.config.RepositoryBeanDefinitionRegistrarSupport;
import org.springframework.data.repository.config.RepositoryConfigurationDelegate;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

import java.lang.annotation.Annotation;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.springframework.beans.factory.config.BeanDefinition.ROLE_INFRASTRUCTURE;
import static org.springframework.data.repository.config.RepositoryConfigurationUtils.exposeRegistration;
import static org.springframework.util.Assert.notNull;

@RequiredArgsConstructor
public class MockRepositoriesRegistrar extends RepositoryBeanDefinitionRegistrarSupport {

    private final ResourceLoader resourceLoader;
    private final Environment environment;

    @Override
    protected Class<? extends Annotation> getAnnotation() {
        return EnableMockRepositories.class;
    }

    @Override
    protected RepositoryConfigurationExtension getExtension() {
        return new MockRepositoryConfigExtension();
    }

    @Override
    public void registerBeanDefinitions(@NonNull AnnotationMetadata metadata, @NonNull BeanDefinitionRegistry registry,
                                        @NonNull BeanNameGenerator generator) {

        // Guard against calls for sub-classes
        if (metadata.getAnnotationAttributes(getAnnotation().getName()) == null) {
            return;
        }

        var configurationSource = new AnnotationRepositoryConfigurationSource(metadata,
                getAnnotation(), resourceLoader, environment, registry, generator) {
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
        };

        var extension = getExtension();
        exposeRegistration(extension, registry, configurationSource);

        var delegate = new RepositoryConfigurationDelegate(configurationSource, resourceLoader, environment);
        delegate.registerRepositoriesIn(registry, extension);
    }
}
