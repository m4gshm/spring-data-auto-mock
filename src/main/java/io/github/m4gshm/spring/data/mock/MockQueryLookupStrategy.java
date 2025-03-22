package io.github.m4gshm.spring.data.mock;

import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.RepositoryQuery;

import java.lang.reflect.Method;

import static org.mockito.Mockito.mock;

public class MockQueryLookupStrategy implements QueryLookupStrategy {
    @Override
    public RepositoryQuery resolveQuery(Method method, RepositoryMetadata metadata,
                                        ProjectionFactory factory, NamedQueries namedQueries) {
        return mock(RepositoryQuery.class);
    }
}
