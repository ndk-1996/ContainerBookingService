package com.interview.maersk.validator;

import com.interview.maersk.constant.ErrorInfo;
import com.interview.maersk.exception.ContainerBookingAppException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ContainerBookingValidatorTest {

    private ContainerBookingValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ContainerBookingValidator();
    }

    @Test
    void shouldNotThrowExceptionForValidContainerSize() {
        // given
        Integer validSize = 20; // pick first valid size

        // when / then
        assertThatCode(() -> validator.validateContainerSize(validSize))
                .doesNotThrowAnyException();
    }

    @Test
    void shouldThrowExceptionForInvalidContainerSize() {
        // given
        Integer invalidSize = 999; // not part of ContainerSize.containerSizes

        // when / then
        assertThatThrownBy(() -> validator.validateContainerSize(invalidSize))
                .isInstanceOf(ContainerBookingAppException.class)
                .hasMessage(ErrorInfo.INVALID_CONTAINER_SIZE.getErrCode() + ": " + ErrorInfo.INVALID_CONTAINER_SIZE.getErrMsg());
    }
}
