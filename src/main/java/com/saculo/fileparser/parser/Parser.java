package com.saculo.fileparser.parser;

import com.saculo.fileparser.customer.Customer;

import java.util.List;

public interface Parser {

    List<Customer> parse(String filePath);
}
