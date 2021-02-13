package exam.bdcc.accountservice;
import exam.bdcc.accountservice.entities.Compte;
import exam.bdcc.accountservice.entities.Operation;
import exam.bdcc.accountservice.feign.ClientRest;
import exam.bdcc.accountservice.repositories.CompteRepository;
import exam.bdcc.accountservice.repositories.OperationRepository;
import exam.bdcc.accountservice.service.OperationsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.kafka.annotation.EnableKafka;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@EnableFeignClients
@EnableKafka
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner start(OperationsService operationsService,
                                   CompteRepository compteRepository,
                                   OperationRepository operationRepository,
                                   ClientRest clientRestClient,
                                   RepositoryRestConfiguration restConfiguration) {
        restConfiguration.exposeIdsFor(Compte.class);
        restConfiguration.exposeIdsFor(Operation.class);
        return args -> {
        };
    }
}