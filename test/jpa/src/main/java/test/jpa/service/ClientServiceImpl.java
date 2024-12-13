package test.jpa.service;

import lombok.RequiredArgsConstructor;
import test.jpa.model.Client;
import org.springframework.stereotype.Service;
import test.jpa.repo.ClientRepository;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Override
    public Client getById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client store(Client client) {
        return clientRepository.save(client);
    }
}
