package com.interview.maersk.exception;

import com.interview.maersk.constant.ErrorInfo;
import com.interview.maersk.dto.ErrorDetailRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ContainerBookingAppException.class)
    public ResponseEntity<ErrorDetailRes> handleContainerBookingAppException(ContainerBookingAppException ex) {
        ErrorDetailRes errorDetailRes = ErrorDetailRes.builder()
                .errCode(ex.getErrorInfo().getErrCode())
                .errMsg(ex.getErrorInfo().getErrMsg())
                .httpStatus(ex.getErrorInfo().getHttpStatus().value())
                .build();

        return ResponseEntity.status(errorDetailRes.getHttpStatus())
                .body(errorDetailRes);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailRes> handleGenericException(Exception ex) {
        ErrorInfo errorInfo = ErrorInfo.UNKNOWN_SERVER_ERROR;
        ErrorDetailRes errorDetailRes = ErrorDetailRes.builder()
                .errCode(errorInfo.getErrCode())
                .errMsg(errorInfo.getErrMsg() + " " + ex.getMessage())
                .httpStatus(errorInfo.getHttpStatus().value())
                .build();

        return ResponseEntity.status(errorDetailRes.getHttpStatus())
                .body(errorDetailRes);
    }
}
