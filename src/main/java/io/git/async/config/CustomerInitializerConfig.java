package io.git.async.config;

import com.github.javafaker.Faker;
import io.git.async.model.Customer;
import io.git.async.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CustomerInitializerConfig implements CommandLineRunner {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public void run(String... args) {

        int max = 99;
        int min = 18;

        Faker faker = new Faker();

        log.info("Starting customer initialization ...");

        for (int i = 0; i < 1000; i++) {
            Customer customer = new Customer();
            customer.setName(faker.name().name());
            customer.setAge((int) (Math.random() * (max - min + 1) + min));
            customer.setJob(faker.job().title());
            customerRepository.save(customer);
        }
        log.info("... finished customer initialization");
    }
}
