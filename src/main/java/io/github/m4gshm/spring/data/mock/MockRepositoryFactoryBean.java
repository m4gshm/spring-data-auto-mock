package io.github.m4gshm.spring.data.mock;

import io.github.m4gshm.spring.data.mock.RepositoryFactory.DefaultRepositoryFactory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

@Getter
@Setter
public class MockRepositoryFactoryBean<T extends Repository<S, ID>, S, ID> extends RepositoryFactoryBeanSupport<T, S, ID> {

    private RepositoryFactory repositoryFactory;
    private QueryLookupStrategyFactory queryLookupStrategyFactory;
    private boolean resettable;

    public MockRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        this(repositoryInterface, true);
    }

    public MockRepositoryFactoryBean(Class<? extends T> repositoryInterface, boolean resettable) {
        super(repositoryInterface);
        this.resettable = resettable;
    }

    @Override
    public void afterPropertiesSet() {
        if (repositoryFactory == null) {
            repositoryFactory = new DefaultRepositoryFactory(resettable);
        }
        if (queryLookupStrategyFactory == null) {
            queryLookupStrategyFactory = QueryLookupStrategyFactory.DEFAULT;
        }
        super.afterPropertiesSet();
    }

    @Override
    protected RepositoryFactorySupport createRepositoryFactory() {
        return new MockRepositoryFactory(repositoryFactory, queryLookupStrategyFactory);
    }


}
