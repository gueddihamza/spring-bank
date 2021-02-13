package exam.bdcc.accountservice.model;
import exam.bdcc.accountservice.entities.Compte;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class Client {
    private Long id;
    private String nom;
    private String email;
    private List<Compte> comptes = new ArrayList<>();
}