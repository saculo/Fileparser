package com.saculo.fileparser.parser;

import com.saculo.fileparser.customer.Contact;
import com.saculo.fileparser.customer.ContactType;
import com.saculo.fileparser.customer.Customer;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class XmlParser implements Parser {

    private static final Logger LOGGER = Logger.getLogger(XmlParser.class.getName());

    public List<Customer> parse(String filePath) {
        List<Customer> customers = new ArrayList<>();

        SAXBuilder saxBuilder = new SAXBuilder();
        File xmlFile = new File(filePath);
        LOGGER.info("Parsing XML file: " + xmlFile.getName());

        try {
            Document document = saxBuilder.build(xmlFile);
            List<Element> persons = extractPersonElements(document);

            for (Element person : persons) {

                Customer customer = parseCustomer(person);
                List<Element> contactChildren = extractContactsElements(person);

                List<Contact> contacts = contactChildren.stream()
                        .map(this::parseContact)
                        .collect(Collectors.toList());

                customer.setContacts(contacts);
                customers.add(customer);
            }
        }
        catch (IOException | JDOMException exc) {
            LOGGER.info(exc.getMessage());
        }

        return customers;
    }

    private List<Element> extractContactsElements(Element person) {
        Element contactsElement = person.getChild("contacts");
        return contactsElement.getChildren();
    }

    private List<Element> extractPersonElements(Document document) {
        Element root = document.getRootElement();
        return root.getChildren("person");
    }

    private Customer parseCustomer(Element element) {
        String name = element.getChildText("name");
        String surname = element.getChildText("surname");
        Integer age = 0;
        if (element.getChildText("age") != null)
            age = Integer.parseInt(element.getChildText("age"));

        return new Customer(name, surname, age);
    }

    private Contact parseContact(Element element) {
        String contactName = element.getText();
        ContactType contactType = resolveContactType(element.getName());

        return new Contact(contactName, contactType);
    }

    private ContactType resolveContactType(String name) {
        switch (name) {
            case "email":
                return ContactType.EMAIL;
            case "phone":
                return ContactType.PHONE;
            case "jabber":
                return ContactType.JABBER;
            default:
                return ContactType.UNKNOWN;
        }
    }
}
