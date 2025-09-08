package io.github.m4gshm.spring.data.mock;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
public class MockRepositoryFactory extends RepositoryFactorySupport {
    private final @NonNull RepositoryFactory repositoryFactory;
    private final @NonNull QueryLookupStrategyFactory queryLookupStrategyFactory;

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
