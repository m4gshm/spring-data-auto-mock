package io.github.m4gshm.spring.data.mock;

import org.springframework.data.repository.core.support.RepositoryComposition.RepositoryFragments;

import java.util.HashMap;
import java.util.Map;

import static io.github.m4gshm.spring.data.mock.MockitoUtils.resettable;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

@FunctionalInterface
public interface RepositoryFactory {
    RepositoryFactory DEFAULT = new RepositoryFactory() {
        private final Map<Class<?>, Object> repos = new HashMap<>();

        @Override
        @SuppressWarnings("unchecked")
        public <T> T getRepository(Class<T> repositoryInterface, RepositoryFragments fragments) {
            var exists = repos.get(repositoryInterface);
            if (exists != null && repositoryInterface.isAssignableFrom(exists.getClass())) {
                return (T) exists;
            }
            T mock = mock(repositoryInterface, resettable().defaultAnswer(CALLS_REAL_METHODS));
            repos.put(repositoryInterface, mock);
            return mock;
        }
    };

    <T> T getRepository(Class<T> repositoryInterface, RepositoryFragments fragments);
}
