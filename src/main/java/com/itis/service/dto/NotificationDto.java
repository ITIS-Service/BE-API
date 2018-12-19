package com.itis.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationDto {

    public enum Category {
        COURSE_STATUS("course.status"), COURSE_POINTS("course.points");

        private String category;

        Category(String category) {
            this.category = category;
        }

        @Override
        public String toString() {
            return category;
        }
    }

    private String title;
    private String body;
    private Category category;

}
