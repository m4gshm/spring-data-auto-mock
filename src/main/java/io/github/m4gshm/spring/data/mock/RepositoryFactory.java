package io.github.m4gshm.spring.data.mock;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.core.support.RepositoryComposition.RepositoryFragments;

import java.util.HashMap;
import java.util.Map;

import static io.github.m4gshm.spring.data.mock.MockitoUtils.resettable;
import static org.mockito.Mockito.*;

@FunctionalInterface
public interface RepositoryFactory {
    <T> T getRepository(Class<T> repositoryInterface, RepositoryFragments fragments);

    @RequiredArgsConstructor
    class DefaultRepositoryFactory implements RepositoryFactory {
        private final Map<Class<?>, Object> repos = new HashMap<>();
        private final boolean resettable;

        @Override
        @SuppressWarnings("unchecked")
        public <T> T getRepository(Class<T> repositoryInterface, RepositoryFragments fragments) {
            var exists = repos.get(repositoryInterface);
            if (exists != null && repositoryInterface.isAssignableFrom(exists.getClass())) {
                return (T) exists;
            }
            T mock = mock(repositoryInterface, (this.resettable ? resettable() : withSettings()).defaultAnswer(CALLS_REAL_METHODS));
            repos.put(repositoryInterface, mock);
            return mock;
        }
    }
}
