package com.itis.service.dto;
/*
 * @author Rustem Khairutdinov
 * @group 11-602
 */

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RequestDto {

    private long requestId;
    private long courseId;
    private String requestType;
    private String name;
    private String changes;
    private Date creatingDate;
}
