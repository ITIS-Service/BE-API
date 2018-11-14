package com.itis.service.dto;
/*
 * @author Rustem Khairutdinov
 * @group 11-602
 */

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentFindingDto {

    private String groupName;
    private String fullName;
}
