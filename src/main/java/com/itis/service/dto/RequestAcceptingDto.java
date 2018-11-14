package com.itis.service.dto;
/*
 * @author Rustem Khairutdinov
 * @group 11-602
 */

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestAcceptingDto {

    private long courseId;
    private long requestId;
    private boolean isAccept;
}
