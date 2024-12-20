package test.jpa.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.jpa.model.Client;
import test.jpa.service.ClientService;

import java.util.List;

import static java.util.Optional.ofNullable;
import static org.springframework.http.ResponseEntity.notFound;


@RestController
@RequestMapping("client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("{id}")
    public ResponseEntity<Client> getById(@PathVariable Long id) {
        return ofNullable(clientService.getById(id)).map(ResponseEntity::ok).orElse(notFound().build());
    }

    @GetMapping
    public List<Client> getByAll() {
        return clientService.getAll();
    }

    @PostMapping
    public Client save(@RequestBody Client client) {
        return clientService.store(client);
    }
}
