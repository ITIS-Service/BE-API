package com.itis.service.notification;

import com.itis.service.dto.NotificationDto;
import com.itis.service.entity.Device;
import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class IOSNotificationManager implements NotificationManager {

    private static final Logger LOG = LoggerFactory.getLogger(IOSNotificationManager.class);

    private final ApnsService apnsService;

    @Autowired
    public IOSNotificationManager(ApnsService apnsService) {
        this.apnsService = apnsService;
    }

    public void sendPushNotification(Device device, NotificationDto notificationDto, Map<String, ?> fields) {
        LOG.info("Sending an iOS push notification...");

        String token = device.getToken();

        String payload = APNS.newPayload()
                .alertBody(notificationDto.getBody())
                .alertTitle(notificationDto.getTitle())
                .category(notificationDto.getCategory())
                .customFields(fields)
                .sound("default")
                .build();

        LOG.info("Payload: " + payload);

        apnsService.push(token, payload);

        LOG.info("The notification has been hopefully send...");
    }
}
