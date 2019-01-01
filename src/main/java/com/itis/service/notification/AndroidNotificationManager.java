package com.itis.service.notification;

import com.itis.service.dto.NotificationDto;
import com.itis.service.entity.Device;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Map;

public class AndroidNotificationManager implements NotificationManager {

    private static final Logger LOG = LoggerFactory.getLogger(AndroidNotificationManager.class);

    private static final String FCM_API_KEY = "AAAA5Z2jOug:APA91bGVONFAnv40l5URJZODIIjPsEPBdHQXEzf4pvCYh81K0IPgc1A1rFKWiHykTnnB3L4mg4pHI1A9OSWvMwZMUplPSucQXMRSNMbki913z7LlTWfQuy0f8rg9j21FhFWlTrIJe0xB";
    private static final String FCM_URL = "https://fcm.googleapis.com/fcm/send";

    @Override
    public void sendPushNotification(Device device, NotificationDto notificationDto, Map<String, ?> fields) {
        LOG.info("Sending an Android push notification...");

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Authorization", "key=" + FCM_API_KEY);
        httpHeaders.set("Content-Type", "application/json");

        JSONObject notification = new JSONObject();
        JSONObject data = new JSONObject();
        JSONObject json = new JSONObject();

        notification.put("title", notificationDto.getTitle());
        notification.put("body", notificationDto.getBody());
        notification.put("notificationType", notificationDto.getCategory().toString());

        for (Map.Entry<String, ?> field : fields.entrySet()) {
            data.put(field.getKey(), field.getValue());
        }

        json.put("notification", notification);
        json.put("data", data);
        json.put("to", device.getToken());


        LOG.info("Payload: " + json.toString());

        HttpEntity<String> httpEntity = new HttpEntity<>(json.toString(), httpHeaders);
        String response = restTemplate.postForObject(FCM_URL, httpEntity, String.class);

        LOG.info("The notification has been sent. Response: " + response);
    }
}
