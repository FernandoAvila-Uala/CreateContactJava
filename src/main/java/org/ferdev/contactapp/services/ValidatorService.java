package org.ferdev.contactapp.services;

import org.ferdev.contactapp.model.ContactRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ValidatorService {
    private static Logger logger = LoggerFactory.getLogger("jsonLogger");

    public List<String> validateContact(ContactRequest contactRequest) {

        List<String> errors = new ArrayList<>();

        logger.info("Validate fields");
        if (Objects.isNull(contactRequest.getFirstName())) {
            errors.add("The first name field  cannot be empty.");
        }
        if (Objects.isNull(contactRequest.getLastName())) {
            errors.add("The last name field  cannot be empty.");
        }
        return errors;
    }
}
