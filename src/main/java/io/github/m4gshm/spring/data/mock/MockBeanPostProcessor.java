package io.github.m4gshm.spring.data.mock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

import static java.lang.reflect.Modifier.FINAL;
import static java.util.Optional.ofNullable;

@Slf4j
@RequiredArgsConstructor
public class MockBeanPostProcessor implements BeanPostProcessor {

    private final ObjectProvider<RepositoryFactory> repositoryFactory;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (!(bean instanceof RepositoryFactoryBeanSupport<?, ?, ?>)) {
            return bean;
        }
        var repositoryFactoryBeanSupport = (RepositoryFactoryBeanSupport<?, ?, ?>) bean;
        var repositoryInterface = repositoryFactoryBeanSupport.getObjectType();

        var repositoryFactory = ofNullable(this.repositoryFactory.getIfAvailable()).orElse(RepositoryFactory.DEFAULT);
        var mockRepositoryFactoryBeanSupport = new MockRepositoryFactoryBeanSupport(repositoryInterface,
                repositoryFactory, repositoryFactoryBeanSupport);
        var fields = RepositoryFactoryBeanSupport.class.getDeclaredFields();
        for (var field : fields) {
            try {
                field.setAccessible(true);
                var fieldValue = field.get(repositoryFactoryBeanSupport);
                if ((field.getModifiers() & FINAL) == 0) {
                    field.set(mockRepositoryFactoryBeanSupport, fieldValue);
                } else {
                    //log
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                log.error(e.getMessage(), e);
            }
        }
        return mockRepositoryFactoryBeanSupport;
    }
}
