package com.saculo.fileparser.parser;


import com.saculo.fileparser.customer.Customer;

import java.util.List;

public class ParserContext {

    private Parser parser;

    public void set(Parser parser) {
        this.parser = parser;
    }

    public List<Customer> parse(String filePath) {
        return parser.parse(filePath);
    }
}
