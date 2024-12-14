package mongo.service;

import lombok.RequiredArgsConstructor;
import mongo.model.Client;
import mongo.repo.ClientRepository;
import org.springframework.stereotype.Service;

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
