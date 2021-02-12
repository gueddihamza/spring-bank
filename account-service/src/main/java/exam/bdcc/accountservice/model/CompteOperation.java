package exam.bdcc.accountservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor
public class CompteOperation {
    private Double montant;

    private Long compteId;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(nullable = true)
    private LocalDateTime date;

    private String type;

}
