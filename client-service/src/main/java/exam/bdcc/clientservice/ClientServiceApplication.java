package exam.bdcc.clientservice;

import exam.bdcc.clientservice.entities.Client;
import exam.bdcc.clientservice.repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class ClientServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientServiceApplication.class, args);
    }
    @Bean
    public CommandLineRunner start(ClientRepository clientRepository, RepositoryRestConfiguration restConfiguration){

        restConfiguration.exposeIdsFor(Client.class);
        return args -> {
            clientRepository.save(new Client(null,"hamza","hamza@gmail.com"));
            clientRepository.save(new Client(null,"john","john@gmail.com"));
            clientRepository.save(new Client(null,"jane","jane@gmail.com"));
        };
    }

}