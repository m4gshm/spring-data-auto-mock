package io.github.m4gshm.spring.data.mock;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.data.repository.config.RepositoryConfigurationExtension;

import java.util.Map;
import java.util.Map.Entry;

import static io.github.m4gshm.spring.data.mock.FakeBeanDefinitionRegistry.getFakeRegistry;
import static io.github.m4gshm.spring.data.mock.FakeRepositoryConfigurationSource.getFakeConfigurationSource;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Getter
@Setter
public class RepositoryReplaceByMockPostProcessor implements BeanFactoryPostProcessor {


    private Object source;
    private boolean resettable;
    private boolean removeSourceExtension;
    private boolean removeGeneratedByExtension;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        var beansOfType = beanFactory.getBeansOfType(RepositoryConfigurationExtension.class);
        if (!(beanFactory instanceof BeanDefinitionRegistry)) {
            //log
            return;
        }
        var registry = (BeanDefinitionRegistry) beanFactory;
        for (var entry : beansOfType.entrySet()) {
            var repositoryConfigurationExtensionBeanName = entry.getKey();
            var repositoryConfigurationExtension = entry.getValue();
            if (!(repositoryConfigurationExtension instanceof MockRepositoryConfigExtension)) {
                var repositoryFactoryBeanClassName = repositoryConfigurationExtension.getRepositoryFactoryBeanClassName();
                final FakeBeanDefinitionRegistry fakeRegistry;
                if (removeGeneratedByExtension) {
                    fakeRegistry = getFakeRegistry();
                    repositoryConfigurationExtension.registerBeansForRoot(fakeRegistry, getFakeConfigurationSource());
                } else {
                    fakeRegistry = null;
                }

                var beanDefinitionNames = beanFactory.getBeanDefinitionNames();
                var repositories = stream(beanDefinitionNames)
                        .map(name -> Map.entry(name, beanFactory.getBeanDefinition(name)))
                        .filter(e -> {
                            var bd = e.getValue();
                            var beanClassName = bd.getBeanClassName();
                            return repositoryFactoryBeanClassName.equals(beanClassName);
                        })
                        .collect(toMap(Entry::getKey, Entry::getValue));

                for (var name : repositories.keySet()) {
                    //log
                    var repository = repositories.get(name);
                    //clear factoryBeanInstanceCache
                    registry.removeBeanDefinition(name);
                    var replacingBeanDefinition = newBeanDefinition(repository);
                    registry.registerBeanDefinition(name, replacingBeanDefinition);
                }
                if (removeSourceExtension) {
                    registry.removeBeanDefinition(repositoryConfigurationExtensionBeanName);
                    if (removeGeneratedByExtension && fakeRegistry != null) {
                        var definitionNames = fakeRegistry.getBeanDefinitionNames();
                        for (var beanDefinitionName : definitionNames) {
                            registry.removeBeanDefinition(beanDefinitionName);
                        }
                    }
                }
            }
        }

    }

    private GenericBeanDefinition newBeanDefinition(BeanDefinition repository) {
        var replacingBeanDefinition = new GenericBeanDefinition();

        replacingBeanDefinition.setBeanClassName(MockRepositoryFactoryBean.class.getName());
        replacingBeanDefinition.setConstructorArgumentValues(repository.getConstructorArgumentValues());
        replacingBeanDefinition.setSource(repository.getSource());
        replacingBeanDefinition.setResourceDescription(repository.getResourceDescription() +
                " mocked by @" + EnableMockRepositories.class + " from " + source);
        return replacingBeanDefinition;
    }

    @RequiredArgsConstructor
    public enum Properties {
        RESETTABLE("resettable"),
        REMOVE_REGISTRY("removeSourceExtension"),
        REMOVE_GENERATED_BY_REGISTRY("removeGeneratedByExtension"),
        SOURCE("source");
        public final String name;
    }

}
