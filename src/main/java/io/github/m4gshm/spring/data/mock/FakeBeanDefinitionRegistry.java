package io.github.m4gshm.spring.data.mock;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class FakeBeanDefinitionRegistry implements BeanDefinitionRegistry {
    public final Map<String, BeanDefinition> map = new ConcurrentHashMap<>();

    public static FakeBeanDefinitionRegistry getFakeRegistry() {
        return new FakeBeanDefinitionRegistry();
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionStoreException {
        map.put(beanName, beanDefinition);
    }

    @Override
    public void removeBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
        throw new UnsupportedOperationException("");
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
        throw new UnsupportedOperationException("");
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return map.containsKey(beanName);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return map.keySet().toArray(new String[0]);
    }

    @Override
    public int getBeanDefinitionCount() {
        return map.size();
    }

    @Override
    public boolean isBeanNameInUse(String beanName) {
        return map.containsKey(beanName);
    }

    @Override
    public void registerAlias(String name, String alias) {
        throw new UnsupportedOperationException("");
    }

    @Override
    public void removeAlias(String alias) {
        throw new UnsupportedOperationException("");
    }

    @Override
    public boolean isAlias(String name) {
        throw new UnsupportedOperationException("");
    }

    @Override
    public String[] getAliases(String name) {
        throw new UnsupportedOperationException("");
    }
}
