package io.git.async.repository;



import io.git.async.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByName(String name);
    List<Customer> findByJob(String job);

}
