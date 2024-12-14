package test;

import io.github.m4gshm.spring.data.mock.EnableAutoRepositoryMocks;
import mongo.MongoApplication;
import mongo.model.Client;
import mongo.repo.ClientRepository;
import mongo.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@EnableAutoRepositoryMocks
@SpringBootTest(classes = {MongoApplication.class, AggregatedRepositoryFactory.class})
public class ServiceTest {

    @Autowired
    ClientService clientService;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AggregatedRepositoryFactory repositoryFactory;

    @Test
    public void simpleMockTest() {
        var client = new Client();
        when(clientRepository.findById(eq(1L))).thenAnswer(invocationOnMock -> Optional.of(client));

        var result = clientService.getById(1L);
        assertSame(client, result);

        assertTrue(repositoryFactory.getRepos().contains(clientRepository));
    }
}
