package io.github.m4gshm.spring.data.mock;

import org.mockito.Mockito;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.AbstractEntityInformation;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import static io.github.m4gshm.spring.data.mock.RepositoryFactory.DEFAULT;

public class MockRepositoryFactory extends RepositoryFactorySupport {
    private final RepositoryFactory repositoryFactory;

    public MockRepositoryFactory(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = (repositoryFactory != null) ? repositoryFactory : DEFAULT;
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
}
