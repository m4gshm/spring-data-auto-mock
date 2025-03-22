package io.github.m4gshm.spring.data.mock;

import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.AbstractEntityInformation;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryLookupStrategy.Key;
import org.springframework.data.repository.query.QueryMethodEvaluationContextProvider;

import java.util.Optional;

import static java.util.Optional.ofNullable;

public class MockRepositoryFactory extends RepositoryFactorySupport {
    private final RepositoryFactory repositoryFactory;
    private final QueryLookupStrategyFactory queryLookupStrategyFactory;

    public MockRepositoryFactory(RepositoryFactory repositoryFactory,
                                 QueryLookupStrategyFactory queryLookupStrategyFactory) {
        this.repositoryFactory = repositoryFactory != null
                ? repositoryFactory
                : RepositoryFactory.DEFAULT;
        this.queryLookupStrategyFactory = queryLookupStrategyFactory != null
                ? queryLookupStrategyFactory
                : QueryLookupStrategyFactory.DEFAULT;
    }

    @Override
    public <T, ID> EntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
        return new AbstractEntityInformation<>(domainClass) {
            @Override
            public ID getId(T entity) {
                throw new UnsupportedOperationException("getId");
            }

            @Override
            public Class<ID> getIdType() {
                throw new UnsupportedOperationException("getIdType");
            }
        };
    }

    @Override
    protected Object getTargetRepository(RepositoryInformation metadata) {
        return this.repositoryFactory.getRepository(metadata.getRepositoryInterface(), null);
    }

    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return metadata.getRepositoryInterface();
    }

    @Override
    protected Optional<QueryLookupStrategy> getQueryLookupStrategy(
            Key key, QueryMethodEvaluationContextProvider evaluationContextProvider) {
        return ofNullable(queryLookupStrategyFactory.getQueryLookupStrategy(key, evaluationContextProvider));
    }
}
