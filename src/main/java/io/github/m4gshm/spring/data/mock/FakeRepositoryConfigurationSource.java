package io.github.m4gshm.spring.data.mock;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.data.repository.config.ImplementationDetectionConfiguration;
import org.springframework.data.repository.config.RepositoryConfigurationSource;
import org.springframework.data.util.Streamable;

import java.util.Optional;

class FakeRepositoryConfigurationSource implements RepositoryConfigurationSource {

    public static RepositoryConfigurationSource getFakeConfigurationSource() {
        return new FakeRepositoryConfigurationSource();
    }

    @Override
    public Object getSource() {
        return "fake source";
    }

    @Override
    public Streamable<String> getBasePackages() {
        return Streamable.empty();
    }

    @Override
    public Optional<Object> getQueryLookupStrategyKey() {
        return Optional.empty();
    }

    @Override
    public Optional<String> getRepositoryImplementationPostfix() {
        return Optional.empty();
    }

    @Override
    public Optional<String> getNamedQueryLocation() {
        return Optional.empty();
    }

    @Override
    public Optional<String> getRepositoryBaseClassName() {
        return Optional.empty();
    }

    @Override
    public Optional<String> getRepositoryFactoryBeanClassName() {
        return Optional.empty();
    }

    @Override
    public Streamable<BeanDefinition> getCandidates(ResourceLoader loader) {
        return Streamable.empty();
    }

    @Override
    public Optional<String> getAttribute(String name) {
        return Optional.empty();
    }

    @Override
    public <T> Optional<T> getAttribute(String name, Class<T> type) {
        return Optional.empty();
    }

    @Override
    public boolean usesExplicitFilters() {
        throw new UnsupportedOperationException("");
    }

    @Override
    public Streamable<TypeFilter> getExcludeFilters() {
        return Streamable.empty();
    }

    @Override
    public String generateBeanName(BeanDefinition beanDefinition) {
        throw new UnsupportedOperationException("");
    }

    @Override
    public ImplementationDetectionConfiguration toImplementationDetectionConfiguration(MetadataReaderFactory factory) {
        throw new UnsupportedOperationException("");
    }

    @Override
    public BootstrapMode getBootstrapMode() {
        return BootstrapMode.LAZY;
    }

    @Override
    public String getResourceDescription() {
        return "fake";
    }
}
