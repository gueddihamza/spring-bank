package exam.bdcc.accountservice.feign;

import exam.bdcc.accountservice.model.Client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "localhost:8080")
public interface ClientRest {
    @GetMapping(path = "/clients/{id}")
    Client getClientById(@PathVariable("id") Long id);

}