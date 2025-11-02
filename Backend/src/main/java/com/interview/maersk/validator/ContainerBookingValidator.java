package com.interview.maersk.validator;

import com.interview.maersk.constant.ContainerSize;
import com.interview.maersk.constant.ErrorInfo;
import com.interview.maersk.exception.ContainerBookingAppException;
import org.springframework.stereotype.Component;

@Component
public class ContainerBookingValidator {

    public void validateContainerSize(Integer containerSize) {
        if (!ContainerSize.containerSizes.contains(containerSize)) {
            throw new ContainerBookingAppException(ErrorInfo.INVALID_CONTAINER_SIZE);
        }
    }
}
