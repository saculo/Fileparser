package com.saculo.fileparser.customer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Contact {

    private Long id;
    private Long customerId;
    private ContactType contactType;
    private String contact;

    public Contact(String contactName, ContactType contactType) {
        this.contact = contactName;
        this.contactType = contactType;
    }
}
