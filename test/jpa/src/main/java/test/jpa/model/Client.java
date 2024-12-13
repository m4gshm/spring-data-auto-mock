package test.jpa.model;


import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class Client {
    @Id
    Long id;
    String name;
}
