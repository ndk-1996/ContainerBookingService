package com.interview.maersk.validator;

import com.interview.maersk.constant.ContainerSize;
import com.interview.maersk.constant.ErrorInfo;
import com.interview.maersk.exception.ContainerBookingAppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ContainerBookingValidator {

    public void validateContainerSize(Integer containerSize) {
        log.info("Validating container size: {}", containerSize);
        if (!ContainerSize.containerSizes.contains(containerSize)) {
            throw new ContainerBookingAppException(ErrorInfo.INVALID_CONTAINER_SIZE);
        }
    }
}
