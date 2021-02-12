package exam.bdcc.accountservice.entities;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime date;

    private Double montant;

    private String type;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    private Compte compte;
}

