package com.interview.maersk.controller;

import com.interview.maersk.constant.ErrorInfo;
import com.interview.maersk.dto.ContainerAvailabilityReq;
import com.interview.maersk.dto.ContainerAvailabilityRes;
import com.interview.maersk.dto.ContainerBookingReq;
import com.interview.maersk.dto.ContainerBookingRes;
import com.interview.maersk.exception.ContainerBookingAppException;
import com.interview.maersk.service.BookingService;
import com.interview.maersk.validator.ContainerBookingValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContainerBookingControllerImplTest {

    @Mock
    private ContainerBookingValidator validator;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private ContainerBookingControllerImpl controller;

    private ContainerAvailabilityReq availabilityReq;
    private ContainerAvailabilityRes availabilityRes;
    private ContainerBookingReq bookingReq;
    private ContainerBookingRes bookingRes;

    @BeforeEach
    void setUp() {
        availabilityReq = new ContainerAvailabilityReq();
        availabilityReq.setContainerSize(20);

        availabilityRes = new ContainerAvailabilityRes();
        availabilityRes.setAvailable(true);

        bookingReq = new ContainerBookingReq();
        bookingReq.setContainerSize(40);

        bookingRes = new ContainerBookingRes();
        bookingRes.setBookingRef("ABC123");
    }

    @Test
    void shouldReturnAvailabilityResponse_whenValidContainerSize() {
        // given
        when(bookingService.checkAvailability(availabilityReq)).thenReturn(Mono.just(availabilityRes));

        // when
        Mono<ResponseEntity<ContainerAvailabilityRes>> responseMono = controller.checkAvailability(availabilityReq);

        // then
        StepVerifier.create(responseMono)
                .assertNext(response -> {
                    assertThat(response.getBody()).isEqualTo(availabilityRes);
                    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
                })
                .verifyComplete();

        verify(validator).validateContainerSize(20);
        verify(bookingService).checkAvailability(availabilityReq);
    }

    @Test
    void shouldReturnBookingResponse_whenValidContainerSize() {
        // given
        when(bookingService.makeBooking(bookingReq)).thenReturn(Mono.just(bookingRes));

        // when
        Mono<ResponseEntity<ContainerBookingRes>> responseMono = controller.makeBooking(bookingReq);

        // then
        StepVerifier.create(responseMono)
                .assertNext(response -> {
                    assertThat(response.getBody()).isEqualTo(bookingRes);
                    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
                })
                .verifyComplete();

        verify(validator).validateContainerSize(40);
        verify(bookingService).makeBooking(bookingReq);
    }

    @Test
    void shouldPropagateException_whenValidatorThrowsError() {
        // given
        availabilityReq.setContainerSize(30);
        doThrow(new ContainerBookingAppException(ErrorInfo.INVALID_CONTAINER_SIZE)).when(validator).validateContainerSize(availabilityReq.getContainerSize());

        assertThatThrownBy(() -> controller.checkAvailability(availabilityReq))
                .isInstanceOf(ContainerBookingAppException.class)
                .hasMessage(ErrorInfo.INVALID_CONTAINER_SIZE.getErrCode() + ": " + ErrorInfo.INVALID_CONTAINER_SIZE.getErrMsg());

        verify(validator).validateContainerSize(30);
        verifyNoInteractions(bookingService);
    }
}
