package org.ferdev.contactapp.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ferdev.contactapp.model.Contact;
import org.ferdev.contactapp.model.ContactRequest;
import org.ferdev.contactapp.repository.DbManager;
import org.ferdev.contactapp.services.ValidatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CreateContactHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static Logger logger = LoggerFactory.getLogger("jsonLogger");
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        ContactRequest contactRequest;
        try {
            logger.info("Create contact handler");
            ValidatorService validatorService = new ValidatorService();
            contactRequest = objectMapper.readValue(input.getBody(), ContactRequest.class);
            List<String> errorList = validatorService.validateContact(contactRequest);

            if (!errorList.isEmpty()) {
                response.setStatusCode(400);
                response.setBody(new ObjectMapper().writeValueAsString(errorList));

                return response;
            } else {
                DbManager dbManager = new DbManager();
                Contact contact = dbManager.saveContact(contactRequest);

                response.setStatusCode(200);
                response.setBody(String.format("{\"message\": \"Contact with ID: %s was successfully saved\"}", contact.getId()));

                return response;
            }

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setBody("Internal server error.");

            return response;
        }
    }
}
