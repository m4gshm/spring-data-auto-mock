package test.jpa.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.jpa.model.Client;
import test.jpa.service.ClientServiceImpl;

@RestController
@RequestMapping("client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientServiceImpl clientService;

    @GetMapping("{id}")
    public Client getById(@PathVariable Long id) {
        return clientService.getById(id);
    }
}
