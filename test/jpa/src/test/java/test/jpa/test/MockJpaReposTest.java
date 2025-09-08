package test.jpa.test;

import io.github.m4gshm.spring.data.mock.EnableMockRepositories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.common.RepositoryAccess;
import test.jpa.model.Client;
import test.jpa.repo.ClientRepository;
import test.jpa.service.ClientService;
import test.jpa.service.ClientServiceImpl;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.aop.support.AopUtils.getTargetClass;

@EnableMockRepositories(basePackageClasses = ClientRepository.class)
@SpringBootTest(classes = {ClientServiceImpl.class, RepositoryAccess.class})
public class MockJpaReposTest {
    @Autowired
    ClientService clientService;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    RepositoryAccess repositoryFactory;

    @Test
    public void mocksTest() {
        var client = new Client();
        when(clientRepository.findById(eq(1L))).thenAnswer(invocationOnMock -> Optional.of(client));

        var result = clientService.getById(1L);
        assertSame(client, result);

        var targetClass = getTargetClass(clientRepository);
        assertTrue(targetClass.getName().contains("$MockitoMock$"));
        var repo = repositoryFactory.getRepo(targetClass);
        assertNotNull(repo);

        verify(clientRepository, times(1)).findById(eq(1L));
    }

    @Test
    public void secondMocksTest() {
        clientService.getById(1L);
        verify(clientRepository, times(1)).findById(eq(1L));
    }
}
