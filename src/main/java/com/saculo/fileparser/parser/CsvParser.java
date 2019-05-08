package com.saculo.fileparser.parser;

import com.saculo.fileparser.customer.Contact;
import com.saculo.fileparser.customer.ContactType;
import com.saculo.fileparser.customer.Customer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class CsvParser implements Parser {

    private static final Logger LOGGER = Logger.getLogger(CsvParser.class.getName());
    private static final String SEPARATOR = ",";
    private static final int FIRST_CONTACT_COLUMN = 4;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);
    private static final Pattern PHONE_PATTERN = Pattern.compile("^[1-9][0-9]{8}");
    private static final Pattern JABBER_PATTERN = Pattern.compile("^[a-zA-Z]*$");

    public List<Customer> parse(String filePath) {
        List<Customer> customers = new ArrayList<>();
        try {
            File csvFile = new File(filePath);
            BufferedReader br = new BufferedReader(new FileReader(csvFile));
            LOGGER.info("Parsing CSV file: " + csvFile.getName());
            String readLine;
            while ((readLine = br.readLine()) != null) {
                Customer customer = createCustomerWithContacts(readLine);
                customers.add(customer);
            }
            br.close();
        }
        catch (IOException exc) {
            LOGGER.info(exc.getMessage());
        }

        return customers;
    }

    private Customer createCustomerWithContacts(String line) {
        String[] columns = line.split(SEPARATOR);
        Customer customer = createCustomer(columns);
        customer.setContacts(createContactsFromRow(columns));

        return customer;
    }

    private Customer createCustomer(String[] columns) {
        String name = columns[0];
        String surname = columns[1];
        Integer age = 0;
        if(!columns[2].isEmpty())
            age = Integer.parseInt(columns[2]);

        return new Customer(name, surname, age);
    }

    private List<Contact> createContactsFromRow(String[] columns) {
        List<Contact> contacts = new ArrayList<>();
        for(int i = FIRST_CONTACT_COLUMN; i < columns.length; i++) {
            contacts.add(new Contact(columns[i], resolveContactType(columns[i])));
        }

        return contacts;
    }

    private ContactType resolveContactType(String column) {
        if(EMAIL_PATTERN.matcher(column).find())
            return ContactType.EMAIL;
        else if (PHONE_PATTERN.matcher(column.replaceAll(" ", "")).find())
            return ContactType.PHONE;
        else if(JABBER_PATTERN.matcher(column).find())
            return ContactType.JABBER;
        else
            return ContactType.UNKNOWN;
    }
}
