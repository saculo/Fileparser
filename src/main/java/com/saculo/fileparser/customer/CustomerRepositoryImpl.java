package com.saculo.fileparser.customer;

import com.saculo.fileparser.infrastructure.DatabaseManager;

import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

public class CustomerRepositoryImpl implements CustomerRepository {

    private static final Logger LOGGER = Logger.getLogger(CustomerRepositoryImpl.class.getName());

    private static final String SAVE_CUSTOMER_QUERY = "INSERT INTO customers(name, surname, age) VALUES (?, ?, ?)";
    private static final String SAVE_CONTACTS_QUERY = "INSERT INTO contacts(id_customer, contactType, contact) VALUES (?, ?, ?)";

    private Connection connection;
    private PreparedStatement statementCustomer;
    private PreparedStatement statementContacts;
    private ResultSet resultSet;
    private ResultSet resultSetContacts;

    @Override
    public Customer saveCustomer(Customer customer) {
        connection = DatabaseManager.getConnection();
        try {
            statementCustomer = connection.prepareStatement(SAVE_CUSTOMER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statementCustomer.setString(1, customer.getName());
            statementCustomer.setString(2, customer.getSurname());
            statementCustomer.setInt(3, customer.getAge());

            statementCustomer.executeUpdate();

            resultSet = statementCustomer.getGeneratedKeys();

            if (resultSet.next()) {
                customer.setId(resultSet.getLong(1));
                saveContacts(customer);
                System.out.println(customer);
            }
            LOGGER.info("Inserted customer into DB!");
            statementCustomer.close();
        }
        catch (SQLException exc) {
            exc.getMessage();
        }
        return customer;
    }

    @Override
    public List<Contact> saveContacts(Customer customer) {
        List<Contact> contacts = customer.getContacts();

        contacts.forEach(contact -> contact.setCustomerId(customer.getId()));
        for (Contact contact : contacts) {
            try {
                statementContacts = connection.prepareStatement(SAVE_CONTACTS_QUERY, Statement.RETURN_GENERATED_KEYS);
                statementContacts.setLong(1, contact.getCustomerId());
                statementContacts.setInt(2, contact.getContactType().getValue());
                statementContacts.setString(3, contact.getContact());

                statementContacts.executeUpdate();

                resultSetContacts = statementContacts.getGeneratedKeys();
                if (resultSetContacts.next()) {
                    contact.setId(resultSetContacts.getLong(1));
                }
                LOGGER.info("Inserted contact into DB!");
            }
            catch (SQLException exc) {
                exc.getMessage();
            }
        }

        return contacts;
    }
}
