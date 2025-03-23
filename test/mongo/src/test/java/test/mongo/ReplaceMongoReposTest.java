package test.mongo;

import io.github.m4gshm.spring.data.mock.EnableMockRepositories;
import mongo.MongoApplication;
import mongo.model.Client;
import mongo.repo.ClientRepository;
import mongo.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.common.RepositoryAccess;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.aop.support.AopUtils.getTargetClass;

@EnableMockRepositories
@SpringBootTest(classes = {MongoApplication.class, RepositoryAccess.class})
public class ReplaceMongoReposTest {

    @Autowired
    ClientService clientService;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    RepositoryAccess repositoryFactory;

    @Test
    public void replaceMongoReposByMocksTest() {
        var client = new Client();
        when(clientRepository.findById(eq(1L))).thenAnswer(invocationOnMock -> Optional.of(client));

        var result = clientService.getById(1L);
        assertSame(client, result);

        var targetClass = getTargetClass(clientRepository);
        assertTrue(targetClass.getName().contains("$MockitoMock$"));
        var repo = repositoryFactory.getRepo(targetClass);
        assertNotNull(repo);
    }

    @Test
    public void secondMocksTest() {
        clientService.getById(1L);
        verify(clientRepository, times(1)).findById(eq(1L));
    }
}
