package test.jpa.service;

import test.jpa.model.Client;

public interface ClientService {
    Client getById(Long id);

    Client store(Client client);

    Iterable<Client> getAll();
}
