package exam.bdcc.accountservice.entities;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import exam.bdcc.accountservice.model.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Compte {
    @Id
    @GeneratedValue
    private Long id;
    private Double solde;
    @JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateCreation;
    private String type;
    private String etat;
    @OneToMany(mappedBy = "compte", fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Operation> operations = new ArrayList<>();
    private Long idClient;
    @Transient
    private Client client;
}



