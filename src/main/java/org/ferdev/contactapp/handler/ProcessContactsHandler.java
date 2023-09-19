package org.ferdev.contactapp.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessContactsHandler implements RequestHandler<DynamodbEvent, Void> {

    private static Logger logger = LoggerFactory.getLogger("jsonLogger");
    private final String snsTopicArn = "arn:aws:sns:us-east-1:620097380428:ContactFerAvilaSNSTopic";

    @Override
    public Void handleRequest(DynamodbEvent event, Context context) {
        AmazonSNS snsClient = AmazonSNSClientBuilder.defaultClient();

        logger.info("Process Contact Handler");
        for (DynamodbEvent.DynamodbStreamRecord record : event.getRecords()) {
            if (record.getEventName().equals("INSERT")) {
                String contactId = record.getDynamodb().getNewImage().get("id").getS();
                sendNotification(snsClient, contactId);
            }
        }
        return null;
    }

    private void sendNotification(AmazonSNS snsClient, String message) {
        PublishRequest request = new PublishRequest(snsTopicArn, message);
        snsClient.publish(request);
    }
}