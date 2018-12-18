package com.itis.service.notification;

import com.itis.service.dto.NotificationDto;
import com.itis.service.entity.Device;

import java.util.Map;

public interface NotificationManager {

    void sendPushNotification(Device device, NotificationDto notificationDto, Map<String, ?> fields);

}
