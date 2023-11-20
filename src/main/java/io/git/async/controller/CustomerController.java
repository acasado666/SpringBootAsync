package io.git.async.controller;

import io.git.async.model.Customer;
import io.git.async.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/customers")
    public List<Customer> getCustomers() throws InterruptedException {
        log.info("Getting ALL customers");
        List<Customer> listCustomers = customerService.getAllCustomers();
        return listCustomers;
    }

    @GetMapping("/customersAsync")
    public CompletableFuture<List<Customer>> getAllCustomersAsync() throws InterruptedException {
        log.info("Getting ALL customers");
        CompletableFuture<List<Customer>> allCustomersAsync = customerService.getAllCustomersAsync();
        return allCustomersAsync;
    }

    @GetMapping("/customers/name")
    public Customer getCustomerByName( @RequestParam(name = "id") String name) throws InterruptedException {
        log.info("Getting customer by name {} ", name);
        return customerService.getCustomerByName(name);
    }

    @GetMapping("/customers/job")
    public List<Customer> getCustomerByJob(@RequestParam(name = "job") String job) throws InterruptedException {
        log.info("Getting customer by job {} ", job);
        List<Customer> jobs = customerService.getCustomerByJob(job);
        return jobs;
    }

    @PostMapping("/customers/save")
    public Customer addCustomer(@RequestBody Customer customer) {
        log.info("Adding user {} to the Database", customer.getName());
        return customerService.addCustomer(customer);
    }
}
