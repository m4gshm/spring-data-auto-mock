package io.github.m4gshm.spring.data.mock;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Annotation replaces repositories with mock ones.
 * Discovers {@link org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport} beans
 * and wraps them with {@link MockRepositoryWrapperFactoryBean}.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(RepositoryReplaceRegistrar.class)
public @interface ReplaceRepositoriesByMocks {

}
