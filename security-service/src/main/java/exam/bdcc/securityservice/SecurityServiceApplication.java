package exam.bdcc.securityservice;

import exam.bdcc.securityservice.entities.AppRole;
import exam.bdcc.securityservice.entities.AppUser;
import exam.bdcc.securityservice.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityServiceApplication.class, args);

    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner start(AccountService accountService){
        return args -> {
            accountService.addNewRole(new AppRole(null,"USER"));
            accountService.addNewRole(new AppRole(null,"ADMIN"));
            accountService.addNewRole(new AppRole(null,"CLIENT_MANAGER"));
            accountService.addNewRole(new AppRole(null,"ACCOUNTS_MANAGER"));
            accountService.addNewRole(new AppRole(null,"OPERATIONS_MANAGER"));

            accountService.addNewUser(new AppUser(null,"hamza","1234",new ArrayList<>()));
            accountService.addNewUser(new AppUser(null,"admin","1234",new ArrayList<>()));
            accountService.addNewUser(new AppUser(null,"john","1234",new ArrayList<>()));
            accountService.addNewUser(new AppUser(null,"jane","1234",new ArrayList<>()));


            accountService.addRoleToUser("hamza","USER");
            accountService.addRoleToUser("admin","USER");
            accountService.addRoleToUser("admin","ADMIN");
            accountService.addRoleToUser("john","CLIENT_MANAGER");
            accountService.addRoleToUser("jane","ACCOUNTS_MANAGER");
            accountService.addRoleToUser("hamza","OPERATIONS_MANAGER");
        };
    }



}
