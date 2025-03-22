package io.github.m4gshm.spring.data.mock;

import lombok.NonNull;
import org.mockito.Mockito;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static org.springframework.util.ClassUtils.isPresent;

public class ResetReositoryMocksTestExecutionListener extends AbstractTestExecutionListener {
    private static final boolean MOCKITO_IS_PRESENT = isPresent("org.mockito.MockSettings",
            ResetReositoryMocksTestExecutionListener.class.getClassLoader());

    private static boolean isSingleton(String name, ConfigurableListableBeanFactory beanFactory,
                                       Collection<String> instantiatedSingletons) {
        return beanFactory.getBeanDefinition(name).isSingleton() && instantiatedSingletons.contains(name);
    }

    private static Object getBean(ConfigurableListableBeanFactory beanFactory, String name) {
        try {
            if (isStandardBeanOrSingletonFactoryBean(beanFactory, name)) {
                return beanFactory.getBean(name);
            }
        } catch (Exception ex) {
            // Continue
        }
        return beanFactory.getSingleton(name);
    }

    private static boolean isStandardBeanOrSingletonFactoryBean(ConfigurableListableBeanFactory beanFactory, String name) {
        var factoryBeanName = BeanFactory.FACTORY_BEAN_PREFIX + name;
        if (beanFactory.containsBean(factoryBeanName)) {
            return ((FactoryBean<?>) beanFactory.getBean(factoryBeanName)).isSingleton();
        }
        return true;
    }

    private static void resetMocks(ApplicationContext applicationContext) {
        if (applicationContext instanceof ConfigurableApplicationContext) {
            resetMocks((ConfigurableApplicationContext) applicationContext);
        }
    }

    private static void resetMocks(ConfigurableApplicationContext applicationContext) {
        var beanFactory = applicationContext.getBeanFactory();
        var instantiatedSingletons = Set.of(beanFactory.getSingletonNames());
        var beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        stream(beanDefinitionNames)
                .filter(name -> isSingleton(name, beanFactory, instantiatedSingletons))
                .map(name -> getBean(beanFactory, name))
                .filter(Objects::nonNull)
                .filter(MockitoUtils::isResettable)
                .forEach(Mockito::reset);
        ofNullable(applicationContext.getParent()).ifPresent(ResetReositoryMocksTestExecutionListener::resetMocks);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 100;
    }

    @Override
    public void afterTestMethod(@NonNull TestContext testContext) {
        if (MOCKITO_IS_PRESENT) {
            resetMocks(testContext.getApplicationContext());
        }
    }
}
