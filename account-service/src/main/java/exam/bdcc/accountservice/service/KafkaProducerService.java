package exam.bdcc.accountservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import exam.bdcc.accountservice.entities.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void send(String topic, Operation operation) {
        log.info("Operation id: " + operation.getId() + ", type" + operation.getType());
        kafkaTemplate.send(topic, operation);
    }

}