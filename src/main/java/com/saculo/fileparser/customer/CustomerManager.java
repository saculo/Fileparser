package com.saculo.fileparser.customer;

import com.saculo.fileparser.parser.ParserContext;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class CustomerManager {

    private ParserContext parserContext;
    private CustomerRepository customerRepository;

    public CustomerManager(ParserContext parserContext, CustomerRepository customerRepository) {
        this.parserContext = parserContext;
        this.customerRepository = customerRepository;
    }

    public List<Customer> saveCustomersFromFile(String filePath) {
        URL resource = ClassLoader.getSystemClassLoader().getResource(filePath);

        return parserContext.parse(resource.getFile())
                .stream()
                .map(v -> customerRepository.saveCustomer(v))
                .collect(Collectors.toList());
    }
}
