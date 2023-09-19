package org.ferdev.contactapp.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ferdev.contactapp.repository.DbManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateContactByIdHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static Logger logger = LoggerFactory.getLogger("jsonLogger");
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

        logger.info("Update Contact Handler");
        try {
            String contactId = input.getPathParameters().get("id");
            String bodyRequest = input.getBody();
            logger.info(String.format("ID contact: %s", contactId));

            DbManager dbManager = new DbManager();

            dbManager.updateContactById(contactId);
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setBody("Internal server error.");
        }

        return response;
    }
}
