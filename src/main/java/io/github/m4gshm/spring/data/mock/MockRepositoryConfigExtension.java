package io.github.m4gshm.spring.data.mock;

import org.springframework.data.repository.config.RepositoryConfigurationExtensionSupport;

public class MockRepositoryConfigExtension extends RepositoryConfigurationExtensionSupport {
    @Override
    protected String getModulePrefix() {
        return "Mock";
    }

    @Override
    public String getRepositoryFactoryBeanClassName() {
        return MockRepositoryFactoryBean.class.getName();
    }
}
