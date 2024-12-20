package test.jpa.service;

import lombok.RequiredArgsConstructor;
import test.jpa.model.Client;
import org.springframework.stereotype.Service;
import test.jpa.repo.ClientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;

    @Override
    public Client getById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client store(Client client) {
        return clientRepository.save(client);
    }
}
