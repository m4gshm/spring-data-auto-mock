package test.jpa;

import io.github.m4gshm.spring.data.mock.EnableAutoRepositoryMocks;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.jpa.model.Client;
import test.jpa.repo.ClientRepository;
import test.jpa.service.ClientService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@EnableAutoRepositoryMocks
@SpringBootTest(classes = {JpaApplication.class, AggregatedRepositoryFactory.class})
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
