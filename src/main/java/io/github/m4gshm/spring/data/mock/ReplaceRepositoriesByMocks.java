package io.github.m4gshm.spring.data.mock;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Annotation enables mock repositories (TODO).
 *
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(RepositoryReplaceRegistrar.class)
public @interface ReplaceRepositoriesByMocks {

}
