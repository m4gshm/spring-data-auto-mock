package io.github.m4gshm.spring.data.mock;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.QueryCreationListener;
import org.springframework.data.repository.core.support.RepositoryComposition.RepositoryFragments;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.core.support.RepositoryMethodInvocationListener;
import org.springframework.data.repository.core.support.RepositoryProxyPostProcessor;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.data.repository.query.QueryMethodEvaluationContextProvider;
import org.springframework.lang.Nullable;

import java.util.Optional;

@RequiredArgsConstructor
public class MockRepositoryFactorySupport extends RepositoryFactorySupport {
    private final RepositoryFactorySupport repositoryFactorySupport;
    private final RepositoryFactory repositoryFactory;

    @Override
    public void setQueryLookupStrategyKey(QueryLookupStrategy.Key key) {
        repositoryFactorySupport.setQueryLookupStrategyKey(key);
    }

    @Override
    public void setNamedQueries(NamedQueries namedQueries) {
        repositoryFactorySupport.setNamedQueries(namedQueries);
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        repositoryFactorySupport.setBeanClassLoader(classLoader);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        repositoryFactorySupport.setBeanFactory(beanFactory);
    }

    @Override
    public void setEvaluationContextProvider(QueryMethodEvaluationContextProvider evaluationContextProvider) {
        repositoryFactorySupport.setEvaluationContextProvider(evaluationContextProvider);
    }

    @Override
    public void setRepositoryBaseClass(Class<?> repositoryBaseClass) {
        repositoryFactorySupport.setRepositoryBaseClass(repositoryBaseClass);
    }

    @Override
    public void addQueryCreationListener(QueryCreationListener<?> listener) {
        repositoryFactorySupport.addQueryCreationListener(listener);
    }

    @Override
    public void addInvocationListener(RepositoryMethodInvocationListener listener) {
        repositoryFactorySupport.addInvocationListener(listener);
    }

    @Override
    public void addRepositoryProxyPostProcessor(RepositoryProxyPostProcessor processor) {
        repositoryFactorySupport.addRepositoryProxyPostProcessor(processor);
    }

    @Override
    public <T> T getRepository(Class<T> repositoryInterface) {
        return repositoryFactorySupport.getRepository(repositoryInterface);
    }

    @Override
    public <T> T getRepository(Class<T> repositoryInterface, Object customImplementation) {
        return repositoryFactorySupport.getRepository(repositoryInterface, customImplementation);
    }

    @Override
    public <T> T getRepository(Class<T> repositoryInterface, RepositoryFragments fragments) {
        return this.repositoryFactory.getRepository(repositoryInterface, fragments);
    }

    @Override
    protected Optional<QueryLookupStrategy> getQueryLookupStrategy(
            @Nullable QueryLookupStrategy.Key key, QueryMethodEvaluationContextProvider evaluationContextProvider
    ) {
        return Optional.empty();
    }

    @Override
    public <T, ID> EntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
        var entityInformation = repositoryFactorySupport.getEntityInformation(domainClass);
        return (EntityInformation<T, ID>) entityInformation;
    }

    @Override
    protected Object getTargetRepository(RepositoryInformation metadata) {
        return this.repositoryFactory.getRepository(metadata.getRepositoryInterface(), null);
    }

    @Override
    @SneakyThrows
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return metadata.getRepositoryInterface();
//        var getRepositoryBaseClassM = MockRepositoryFactoryBeanSupport.getMethod(repositoryFactorySupport.getClass(),
//                "getRepositoryBaseClass", RepositoryMetadata.class);
//        getRepositoryBaseClassM.setAccessible(true);
//        return (Class<?>) getRepositoryBaseClassM.invoke(repositoryFactorySupport, metadata);
    }

}
