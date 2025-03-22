package test;

import io.github.m4gshm.spring.data.mock.ReplaceRepositoriesByMocks;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.jpa.JpaApplication;
import test.jpa.model.Client;
import test.jpa.repo.ClientRepository;
import test.jpa.service.ClientService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.aop.support.AopUtils.getTargetClass;

@ReplaceRepositoriesByMocks
@SpringBootTest(classes = {JpaApplication.class, AggregatedRepositoryFactory.class})
public class ReplaceJpaReposTest {

    @Autowired
    ClientService clientService;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AggregatedRepositoryFactory repositoryFactory;

    @Test
    public void replaceJpaReposByMockTest() {
        var client = new Client();
        when(clientRepository.findById(eq(1L))).thenAnswer(invocationOnMock -> Optional.of(client));

        var result = clientService.getById(1L);
        assertSame(client, result);

        var targetClass = getTargetClass(clientRepository);
        assertTrue(targetClass.getName().contains("$MockitoMock$"));
        var repos = repositoryFactory.getRepos(targetClass);
        assertEquals(1, repos.size());
        verify(clientRepository, times(1)).findById(eq(1L));
    }

    @Test
    public void secondMocksTest() {
        clientService.getById(1L);
        verify(clientRepository, times(1)).findById(eq(1L));
    }
}
