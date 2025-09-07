package test.jpa.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import test.jpa.JpaApplication;
import test.jpa.repo.ClientRepository;
import test.jpa.service.ClientService;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {JpaApplication.class})
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class MockBeanAnnotationBasedTest {

    @Autowired
    ClientService clientService;
    @MockBean
    ClientRepository clientRepository;

    @Test
    public void secondMocksTest() {
        var client = clientService.getById(1L);
        assertNull(client);
        verify(clientRepository, times(1)).findById(eq(1L));
    }
}
