package test;

import io.github.m4gshm.spring.data.mock.EnableMockRepositories;
import mongo.model.Client;
import mongo.repo.ClientRepository;
import mongo.service.ClientService;
import mongo.service.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.aop.support.AopUtils.getTargetClass;

@EnableMockRepositories(basePackageClasses = ClientRepository.class)
@SpringBootTest(classes = {ClientServiceImpl.class, AggregatedRepositoryFactory.class})
public class MockReposTest {
    @Autowired
    ClientService clientService;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AggregatedRepositoryFactory repositoryFactory;

    @Test
    public void mocksTest() {
        var client = new Client();
        when(clientRepository.findById(eq(1L))).thenAnswer(invocationOnMock -> Optional.of(client));

        var result = clientService.getById(1L);
        assertSame(client, result);

        var targetClass = getTargetClass(clientRepository);
        assertTrue(targetClass.getName().contains("$MockitoMock$"));
        var repos = repositoryFactory.getRepos(targetClass);
        assertEquals(1, repos.size());
    }
}
