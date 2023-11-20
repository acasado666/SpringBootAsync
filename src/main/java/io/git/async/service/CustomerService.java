package io.git.async.service;

import io.git.async.model.Customer;
import io.git.async.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Customer getCustomerByName(String name) throws InterruptedException {
        log.info("Getting customers {} from the repository.", name);
        Optional<Customer> customerOpt = customerRepository.findByName(name);
        if (customerOpt.isEmpty()) {
            log.info("No customer found by name {}", name);
            return new Customer();
        } else {
            log.info("found customer by name {}", name);
            return customerOpt.get();
        }
    }

    public List<Customer> getCustomerByJob(String job) throws InterruptedException {
        log.info("Getting customers {} from the repository.", job);
        List<Customer> customerList = customerRepository.findByJob(job);

        if (CollectionUtils.isEmpty(customerList)) {
            log.info("No customer found by name {}", job);
            return new ArrayList<Customer>();
        }
        log.info("found {} customers by name {}", customerList.size(), job);
        return customerList;
    }

    @Async("asyncTaskExecutor")
    public CompletableFuture<List<Customer>> getAllCustomersAsync() throws InterruptedException {
        log.info("Getting ALL customer using Asynch thread");
        List<Customer> customerList = customerRepository.findAll();
        return CompletableFuture.completedFuture(customerList);
    }
    public List<Customer> getAllCustomers() {
        log.info("Getting ALL customer from the repository.");
        List<Customer> customerList = customerRepository.findAll();

        if (CollectionUtils.isEmpty(customerList)) {
            log.info("No customers found");
            return new ArrayList<Customer>();
        }
        log.info("found {} customers ", customerList.size());
        return customerList;
    }
    public CompletableFuture<List<Customer>> getCustomers() throws InterruptedException {
        log.info("Getting ALL customers from the repository.");
        List<Customer> customerList = customerRepository.findAll();

        if (CollectionUtils.isEmpty(customerList)) {
            log.info("No customers found");
            return CompletableFuture.completedFuture(new ArrayList<Customer>());
        }
        log.info("found {} customers ", customerList.size());
        return CompletableFuture.completedFuture(customerList);
    }
    public Customer addCustomer(Customer customer) {
        log.info("Adding customer {} to database", customer.getName());
        Customer customerSaved = customerRepository.save(customer);
        log.info("Added customer {} successfully", customerSaved.getName());
        return customerSaved;
    }
}
