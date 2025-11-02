package com.interview.maersk.constant;

import lombok.Getter;

@Getter
public enum ErrorInfo {

    INVALID_CONTAINER_SIZE(
            "Provided container size is not supported",
            "CNTR_BKNG_001"
    );

    private final String errMsg;
    private final String errCode;

    ErrorInfo(String errMsg, String errCode) {
        this.errMsg = errMsg;
        this.errCode = errCode;
    }
}
