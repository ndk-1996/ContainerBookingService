package com.interview.maersk.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ErrorDetailRes {

    private String errMsg;
    private String errCode;
    private int httpStatus;
}
