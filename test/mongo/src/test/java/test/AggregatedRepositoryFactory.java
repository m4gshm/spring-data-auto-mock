package test;

import io.github.m4gshm.spring.data.mock.RepositoryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.core.support.RepositoryComposition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RequiredArgsConstructor
public class AggregatedRepositoryFactory implements RepositoryFactory {
    private final Set<Object> repos = new HashSet<>();

    @Override
    public <T> T getRepository(Class<T> repositoryInterface, RepositoryComposition.RepositoryFragments fragments) {
        var repository = DEFAULT.getRepository(repositoryInterface, fragments);
        repos.add(repository);
        return repository;
    }

    public List<Object> getRepos() {
        return new ArrayList<>(repos);
    }
}
