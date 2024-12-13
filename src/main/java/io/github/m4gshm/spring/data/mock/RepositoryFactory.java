package io.github.m4gshm.spring.data.mock;

import org.springframework.data.repository.core.support.RepositoryComposition;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

@FunctionalInterface
public interface RepositoryFactory {
    RepositoryFactory DEFAULT = new RepositoryFactory() {
        @Override
        public <T> T getRepository(Class<T> repositoryInterface, RepositoryComposition.RepositoryFragments fragments) {
            return mock(repositoryInterface, CALLS_REAL_METHODS);
        }
    };

    <T> T getRepository(Class<T> repositoryInterface, RepositoryComposition.RepositoryFragments fragments);
}
