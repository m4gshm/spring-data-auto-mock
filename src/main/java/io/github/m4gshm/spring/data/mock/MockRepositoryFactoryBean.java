package io.github.m4gshm.spring.data.mock;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class MockRepositoryFactoryBean<T extends Repository<S, ID>, S, ID> extends RepositoryFactoryBeanSupport<T, S, ID> {

    private final RepositoryFactory repositoryFactory;

    protected MockRepositoryFactoryBean(Class<? extends T> repositoryInterface,
                                        ObjectProvider<RepositoryFactory> repositoryFactory) {
        super(repositoryInterface);
        this.repositoryFactory = repositoryFactory.getIfAvailable(() -> RepositoryFactory.DEFAULT);
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory() {
        return new MockRepositoryFactory(repositoryFactory, QueryLookupStrategyFactory.DEFAULT);
    }
}
