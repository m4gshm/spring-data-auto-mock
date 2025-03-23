package io.github.m4gshm.spring.data.mock;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

import static java.lang.reflect.Modifier.FINAL;

@Slf4j
@Getter
@Setter
public class RepositoryReplaceByMockPostProcessor implements BeanPostProcessor {

    private boolean resettable;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (!(bean instanceof RepositoryFactoryBeanSupport<?, ?, ?>) || bean instanceof MockRepositoryFactoryBean) {
            return bean;
        }
        var repositoryFactoryBeanSupport = (RepositoryFactoryBeanSupport<?, ?, ?>) bean;
        var repositoryInterface = repositoryFactoryBeanSupport.getObjectType();

        var mockRepositoryFactoryBeanSupport = new MockRepositoryFactoryBean(repositoryInterface, resettable);
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
