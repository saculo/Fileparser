package com.saculo.fileparser.customer;

import java.util.List;

public interface CustomerRepository {

    Customer saveCustomer(Customer customer);
    List<Contact> saveContacts(Customer customer);
}
