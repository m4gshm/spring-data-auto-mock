package io.github.m4gshm.spring.data.mock;

import lombok.SneakyThrows;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import java.lang.reflect.Method;

import static io.github.m4gshm.spring.data.mock.RepositoryFactory.DEFAULT;
import static java.util.Objects.requireNonNull;

public class MockRepositoryFactoryBeanSupport extends RepositoryFactoryBeanSupport {

    private final RepositoryFactory repositoryFactory;
    private final RepositoryFactoryBeanSupport<? extends Repository<?, ?>, ?, ?> repositoryFactoryBean;

    public MockRepositoryFactoryBeanSupport(
            Class<? extends Repository<?, ?>> repositoryInterface, RepositoryFactory repositoryFactory,
            RepositoryFactoryBeanSupport<? extends Repository<?, ?>, ?, ?> repositoryFactoryBean
    ) {
        super(repositoryInterface);
        this.repositoryFactory = repositoryFactory != null ? repositoryFactory : DEFAULT;
        this.repositoryFactoryBean = repositoryFactoryBean;
    }

    public static Method getMethod(Class<?> aClass, String methodName, Class<?>... parameterTypes) {
        var c = aClass;
        while (c != null && Object.class != c) try {
            return c.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            c = c.getSuperclass();
        }
        return null;
    }

    @Override
    @SneakyThrows
    protected RepositoryFactorySupport createRepositoryFactory() {
        Class<?> aClass = repositoryFactoryBean.getClass();
        var createRepositoryFactoryM = getMethod(aClass, "createRepositoryFactory");
        requireNonNull(createRepositoryFactoryM,
                repositoryFactoryBean.getClass().getName() + ".createRepositoryFactory"
        ).setAccessible(true);
        var repositoryFactorySupport = (RepositoryFactorySupport) createRepositoryFactoryM.invoke(repositoryFactoryBean);
        return new MockRepositoryFactorySupport(repositoryFactorySupport, repositoryFactory);
    }

}
