package com.saculo.fileparser.parser;

import com.saculo.fileparser.customer.Contact;
import com.saculo.fileparser.customer.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlParserTest {

    private Parser parser;
    private String file;

    @BeforeEach
    public void init() {
        parser = new XmlParser();
        file = ClassLoader.getSystemClassLoader().getResource("dane-osoby.xml").getFile();
    }

    @Test
    public void shouldParseXmlFile() {
        List<Customer> customers = parser.parse(file);

        assertEquals(2, customers.size());
    }

    @Test
    public void shouldParseContacts() {
        List<Customer> customers = parser.parse(file);
        List<Contact> firstCustomerContacts = customers.get(0).getContacts();
        List<Contact> secondCustomerContacts = customers.get(1).getContacts();

        assertEquals(4, firstCustomerContacts.size());
        assertEquals(4, secondCustomerContacts.size());
    }

}
