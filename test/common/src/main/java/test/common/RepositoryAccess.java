package test.common;

import io.github.m4gshm.spring.data.mock.MockRepositoryFactoryBean;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;


@RequiredArgsConstructor
public class RepositoryAccess {
    private final ApplicationContext applicationContext;

    private static boolean isReposTypeOf(Class<?> type, RepositoryFactoryBeanSupport b) {
        return b.getRepositoryInformation().getRepositoryInterface().isAssignableFrom(type);
    }

    public Object getRepo(Class<?> type) {
        return Stream.of(MockRepositoryFactoryBean.class)
                .map(applicationContext::getBeansOfType).map(Map::values)
                .flatMap(Collection::stream)
                .filter(bean -> isReposTypeOf(type, bean))
                .map(RepositoryFactoryBeanSupport::getObject).findFirst().orElse(null);
    }
}
