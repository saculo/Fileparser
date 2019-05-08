package com.saculo.fileparser;

import com.saculo.fileparser.customer.CustomerRepository;
import com.saculo.fileparser.customer.CustomerRepositoryImpl;
import com.saculo.fileparser.customer.CustomerManager;
import com.saculo.fileparser.parser.CsvParser;
import com.saculo.fileparser.parser.ParserContext;
import com.saculo.fileparser.parser.XmlParser;

public class Main {

    public static void main(String[] args) {
        CustomerRepository customerRepository = new CustomerRepositoryImpl();
        ParserContext parserContext = new ParserContext();
        CustomerManager customerManager = new CustomerManager(parserContext, customerRepository);
        parserContext.set(new XmlParser());
        customerManager.saveCustomersFromFile("dane-osoby.xml");
        parserContext.set(new CsvParser());
        customerManager.saveCustomersFromFile("dane-osoby.txt");
    }
}
