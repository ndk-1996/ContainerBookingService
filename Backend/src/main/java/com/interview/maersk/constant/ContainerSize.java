package com.interview.maersk.constant;

import com.interview.maersk.exception.ContainerBookingAppException;

public enum ContainerSize {

    TWENTY(20),
    FORTY(40);

    private final int value;

    ContainerSize(int value) {
        this.value = value;
    }

    public ContainerSize getContainerSize(int value) {
        for (ContainerSize containerSize : ContainerSize.values()) {
            if (containerSize.value == value) {
                return containerSize;
            }
        }

        throw new ContainerBookingAppException(ErrorInfo.INVALID_CONTAINER_SIZE);
    }
}
