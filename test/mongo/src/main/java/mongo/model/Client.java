package mongo.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;


@Getter
@Setter
@ToString
@Persistent
@NoArgsConstructor
public class Client {
    @Id
    Long id;
    String name;
}
