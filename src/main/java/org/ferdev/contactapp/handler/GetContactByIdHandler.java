package org.ferdev.contactapp.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ferdev.contactapp.model.Contact;
import org.ferdev.contactapp.repository.DbManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class GetContactByIdHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static Logger logger = LoggerFactory.getLogger("jsonLogger");

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

        logger.info("Get Contact Handler");
        try {
            String contactId = input.getPathParameters().get("id");
            logger.info(String.format("ID contact: %s", contactId));

            DbManager dbManager = new DbManager();

            Contact contact = dbManager.getContactById(contactId);

            if (Objects.nonNull(contact)) {
                response.setStatusCode(200);
                response.setBody(new ObjectMapper().writeValueAsString(contact));
            } else {
                response.setStatusCode(404);
                response.setBody("Contact not found.");
            }
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setBody("Internal server error.");
        }

        return response;
    }
}
