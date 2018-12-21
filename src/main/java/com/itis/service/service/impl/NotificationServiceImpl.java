package com.itis.service.service.impl;

import com.itis.service.dto.NotificationDto;
import com.itis.service.entity.Course;
import com.itis.service.entity.Device;
import com.itis.service.entity.Point;
import com.itis.service.entity.Student;
import com.itis.service.entity.enums.UserCourseStatus;
import com.itis.service.notification.NotificationManager;
import com.itis.service.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationManager iOSNotificationManager;
    private final NotificationManager androidNotificationManager;

    @Autowired
    public NotificationServiceImpl(
            @Qualifier("iOSNotificationManager") NotificationManager iOSNotificationManager,
            @Qualifier("androidNotificationManager") NotificationManager androidNotificationManager) {
        this.iOSNotificationManager = iOSNotificationManager;
        this.androidNotificationManager = androidNotificationManager;
    }

    public void sendCourseStatusChanged(Student student, Course course, UserCourseStatus status) {
        if (!student.getUserSettings().isCourseStatusNotificationEnabled()) {
            return;
        }

        String title = "Изменение статуса";
        String body = null;

        switch (status) {
            case WAITING:
                body = String.format("Статус курса %s изменен на ожидание", course.getName());
                break;
            case ACCEPTED:
                body = String.format("Вы были приняты на курс %s", course.getName());
                break;
            case MOVED:
                body = String.format("Вы были перемещены с курса %s", course.getName());
                break;
            case REJECTED:
                body = String.format("Ваш запрос на запись курса %s был отклонен", course.getName());
                break;
        }

        Map<String, Long> fields = new HashMap<>();
        fields.put("courseID", course.getId());

        NotificationDto notificationDto = new NotificationDto(title, body, NotificationDto.Category.COURSE_STATUS);

        for (Device device : student.getDevices()) {
            sendNotification(device, notificationDto, fields);
        }
    }

    public void sendPointChanged(Student student, Course course, Point point) {
        if (!student.getUserSettings().isPointsNotificationEnabled()) {
            return;
        }

        String title = "Изменение баллов";
        String body;

        if (point.getCount() > 0) {
            if (point.getCount() == 1) {
                body = String.format("Добавлен %d балл по курсу %s", point.getCount(), course.getName());
            } else if (isBetween(point.getCount())) {
                body = String.format("Добавлено %d балла по курсу %s", point.getCount(), course.getName());
            } else {
                body = String.format("Добавлено %d баллов по курсу %s", point.getCount(), course.getName());
            }
        } else {
            if (point.getCount() == 1) {
                body = String.format("Вычтен %d балл по курсу %s", point.getCount(), course.getName());
            } else if (isBetween(point.getCount())) {
                body = String.format("Вычтено %d балла по курсу %s", point.getCount(), course.getName());
            } else {
                body = String.format("Вычтено %d баллов по курсу %s", point.getCount(), course.getName());
            }
        }

        Map<String, Long> fields = new HashMap<>();
        fields.put("courseID", course.getId());

        NotificationDto notificationDto = new NotificationDto(title, body, NotificationDto.Category.COURSE_POINTS);

        for (Device device : student.getDevices()) {
            sendNotification(device, notificationDto, fields);
        }
    }

    private void sendNotification(Device device, NotificationDto notificationDto, Map<String, ?> fields) {
        switch (device.getType()) {
            case IOS:
                iOSNotificationManager.sendPushNotification(device, notificationDto, fields);
                break;
            case ANDROID:
                androidNotificationManager.sendPushNotification(device, notificationDto, fields);
                break;
        }
    }

    private boolean isBetween(int x) {
        return 2 <= x && x <= 4;
    }
}
