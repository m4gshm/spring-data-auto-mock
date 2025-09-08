package io.github.m4gshm.spring.data.mock;

import io.github.m4gshm.spring.data.mock.RepositoryReplaceByMockPostProcessor.Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.data.repository.config.AnnotationRepositoryConfigurationSource;
import org.springframework.data.repository.config.RepositoryConfigurationExtensionSupport;
import org.springframework.data.repository.config.RepositoryConfigurationSource;

public class MockRepositoryConfigExtension extends RepositoryConfigurationExtensionSupport {

    private static void setBoolean(BeanDefinitionBuilder builder, Properties dest, boolean value) {
        builder.addPropertyValue(dest.name, value);
    }

    private static boolean getBoolean(RepositoryConfigurationSource config, AnnotationAttributes src, boolean def) {
        return config.getAttribute(src.name, Boolean.class).orElse(def);
    }

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
        setBoolean(builder, Properties.RESETTABLE, getBoolean(config, AnnotationAttributes.RESET_AFTER_TEST, true));
    }

    @RequiredArgsConstructor
    public enum AnnotationAttributes {
        RESET_AFTER_TEST("resetAfterTest");

        public final String name;
    }

}
