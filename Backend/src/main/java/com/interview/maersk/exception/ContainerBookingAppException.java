package com.interview.maersk.exception;

import com.interview.maersk.constant.ErrorInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ContainerBookingAppException extends RuntimeException {

    private final ErrorInfo errorInfo;

    public ContainerBookingAppException(ErrorInfo errorInfo) {
        super(errorInfo.getErrCode() + ": " + errorInfo.getErrMsg());
        this.errorInfo = errorInfo;
    }

    public ContainerBookingAppException(ErrorInfo errorInfo, Throwable cause) {
        super(errorInfo.getErrCode() + ": " + errorInfo.getErrMsg(), cause);
        this.errorInfo = errorInfo;
    }
}
