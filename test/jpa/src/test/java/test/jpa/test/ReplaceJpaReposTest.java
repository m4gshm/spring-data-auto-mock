package test.jpa.test;

import io.github.m4gshm.spring.data.mock.ReplaceRepositoriesByMocks;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import test.common.RepositoryAccess;
import test.jpa.JpaApplication;
import test.jpa.model.Client;
import test.jpa.repo.ClientRepository;
import test.jpa.service.ClientService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.aop.support.AopUtils.getTargetClass;

@ReplaceRepositoriesByMocks
@SpringBootTest(classes = {JpaApplication.class, RepositoryAccess.class})
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class ReplaceJpaReposTest {

    @Autowired
    ClientService clientService;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    RepositoryAccess repositoryFactory;
    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void replaceJpaReposByMockTest() {
        var client = new Client();
        when(clientRepository.findById(eq(1L))).thenAnswer(invocationOnMock -> Optional.of(client));

        var result = clientService.getById(1L);
        assertSame(client, result);

        var targetClass = getTargetClass(clientRepository);
        assertTrue(targetClass.getName().contains("$MockitoMock$"));
        var repo = repositoryFactory.getRepo(targetClass);
        assertNotNull(repo);
        verify(clientRepository, times(1)).findById(eq(1L));

        assertFalse(applicationContext.containsBean(JpaRepositoryFactoryBean.class.getName()));
        assertFalse(applicationContext.containsBean(JpaRepositoryFactoryBean.class.getName()));

        List.of(
                "emBeanDefinitionRegistrarPostProcessor",
                "org.springframework.context.annotation.internalPersistenceAnnotationProcessor",
                "jpaMappingContext",
                "org.springframework.data.jpa.repository.support.JpaEvaluationContextExtension",
                "org.springframework.data.jpa.util.JpaMetamodelCacheCleanup",
                "jpaContext"
        ).forEach(name -> {
            assertFalse(applicationContext.containsBean(name));
        });

    }

    @Test
    public void secondMocksTest() {
        var client = clientService.getById(1L);
        assertNull(client);
        verify(clientRepository, times(1)).findById(eq(1L));
    }
}
