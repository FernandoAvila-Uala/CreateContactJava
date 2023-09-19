package org.ferdev.contactapp.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import org.ferdev.contactapp.repository.DbManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessSnsNotificationHandler implements RequestHandler<SNSEvent, Void> {

    private static Logger logger = LoggerFactory.getLogger("jsonLogger");

    @Override
    public Void handleRequest(SNSEvent event, Context context) {
        logger.info("Process SNS Notification Handler");
        for (SNSEvent.SNSRecord record : event.getRecords()) {
            logger.info("Event - " + record.getSNS().getMessageId()
                    + " " + record.getSNS().getMessage()
                    + " " + record.getSNS().getTopicArn()
                    + " " + record.getSNS().getType());
            String contactId = record.getSNS().getMessage();
            logger.info(String.format("ID: %s", contactId));
            DbManager dbManager = new DbManager();

            dbManager.updateContactById(contactId.replaceAll(" ", ""));
        }
        return null;
    }
}