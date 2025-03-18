package test;

import io.github.m4gshm.spring.data.mock.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.core.support.RepositoryComposition.RepositoryFragments;

import java.util.*;


@RequiredArgsConstructor
public class AggregatedRepositoryFactory implements RepositoryFactory {
    private final Map<Class, Set<Object>> repos = new HashMap<>();

    @Override
    public <T> T getRepository(Class<T> repositoryInterface, RepositoryFragments fragments) {
        var repository = DEFAULT.getRepository(repositoryInterface, fragments);
        repos.computeIfAbsent(repository.getClass(), k -> new HashSet<>()).add(repository);
        return repository;
    }

    public Collection<Object> getRepos(Class type) {
        return repos.getOrDefault(type, Set.of());
    }
}
