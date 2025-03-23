package io.github.m4gshm.spring.data.mock;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.data.repository.config.AnnotationRepositoryConfigurationSource;
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

    @Override
    public void postProcess(BeanDefinitionBuilder builder, AnnotationRepositoryConfigurationSource config) {
        super.postProcess(builder, config);
        boolean resetAfterTest = config.getAttribute("resetAfterTest", Boolean.class).orElse(true);
        builder.addPropertyValue("resettable", resetAfterTest);
    }

}
