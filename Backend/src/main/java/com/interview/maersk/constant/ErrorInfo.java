package com.interview.maersk.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorInfo {

    INVALID_CONTAINER_SIZE(
            "Provided container size is not supported.",
            "CNTR_BKNG_001",
            HttpStatus.BAD_REQUEST
    ),
    INTERNAL_SERVER_ERROR(
            "An internal server error occurred.",
            "CNTR_BKNG_002",
            HttpStatus.INTERNAL_SERVER_ERROR
    );

    private final String errMsg;
    private final String errCode;
    private final HttpStatus httpStatus;

    ErrorInfo(String errMsg, String errCode, HttpStatus httpStatus) {
        this.errMsg = errMsg;
        this.errCode = errCode;
        this.httpStatus = httpStatus;
    }
}
