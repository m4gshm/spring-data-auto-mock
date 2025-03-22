package test.jpa.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import test.jpa.model.Client;

import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {
    @Override
    List<Client> findAll();

    @Query("c.id from Client c where c.name= :name")
    List<Long> findIdsByName(String name);
}
