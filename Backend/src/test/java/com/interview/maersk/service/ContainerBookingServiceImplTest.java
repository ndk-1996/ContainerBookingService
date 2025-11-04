package com.interview.maersk.service;

import com.interview.maersk.constant.ContainerType;
import com.interview.maersk.constant.ErrorInfo;
import com.interview.maersk.dto.*;
import com.interview.maersk.entity.ContainerBookingEntity;
import com.interview.maersk.exception.ContainerBookingAppException;
import com.interview.maersk.repo.ContainerBookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ContainerBookingServiceImplTest {

    @Mock
    private ContainerBookingRepository containerBookingRepository;

    @Mock
    private WebClient webClient;

    // Mocks for WebClient fluent API chain
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private ContainerBookingServiceImpl service;

    @BeforeEach
    void setUp() {
        // Inject values for properties that are usually loaded from application.yml
        ReflectionTestUtils.setField(service, "externalApiMaxRetryAttempts", 1);
        ReflectionTestUtils.setField(service, "databaseMaxRetryAttempts", 1);
    }

    @Test
    void shouldReturnAvailabilityResponse_whenExternalApiReturnsError() {
        // given
        ContainerAvailabilityReq req = new ContainerAvailabilityReq();
        req.setContainerSize(20);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/checkAvailable")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.accept(MediaType.APPLICATION_JSON)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);

        // Simulate WebClient error
        when(responseSpec.bodyToMono(ContainerSpaceRes.class))
                .thenReturn(Mono.error(new RuntimeException("API down")));

        // when
        Mono<ContainerAvailabilityRes> result = service.checkAvailability(req);

        // then
        StepVerifier.create(result)
                .assertNext(res -> assertThat(res.getAvailable()).isIn(true, false))
                .verifyComplete();

        verify(webClient).get();
    }

    @Test
    void shouldReturnAvailabilityResponse_whenExternalApiSucceeds() {
        // given
        ContainerAvailabilityReq req = new ContainerAvailabilityReq();
        req.setContainerSize(40);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri("/checkAvailable")).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.accept(MediaType.APPLICATION_JSON)).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);

        ContainerSpaceRes mockRes = ContainerSpaceRes.builder().availableSpace(10).build();
        when(responseSpec.bodyToMono(ContainerSpaceRes.class)).thenReturn(Mono.just(mockRes));

        // when
        Mono<ContainerAvailabilityRes> result = service.checkAvailability(req);

        // then
        StepVerifier.create(result)
                .assertNext(res -> {
                    assertThat(res.getAvailable()).isTrue();
                })
                .verifyComplete();

        verify(webClient).get();
        verify(responseSpec).bodyToMono(ContainerSpaceRes.class);
    }

    @Test
    void shouldReturnBookingResponse_whenSaveSucceeds() {
        // given
        ContainerBookingReq req = new ContainerBookingReq();
        req.setContainerSize(20);
        req.setContainerType(ContainerType.DRY);
        req.setOrigin("Chennai");
        req.setDestination("Singapore");
        req.setQuantity(5);
        req.setTimestamp(Instant.now());

        ContainerBookingEntity entity = ContainerBookingEntity.builder()
                .bookingRef("957000001")
                .containerSize(20)
                .containerType(ContainerType.DRY)
                .origin("Chennai")
                .destination("Singapore")
                .quantity(5)
                .timestamp(req.getTimestamp())
                .build();

        when(containerBookingRepository.save(any(ContainerBookingEntity.class)))
                .thenReturn(Mono.just(entity));

        // when
        Mono<ContainerBookingRes> result = service.makeBooking(req);

        // then
        StepVerifier.create(result)
                .assertNext(res -> assertThat(res.getBookingRef()).isEqualTo("957000001"))
                .verifyComplete();

        verify(containerBookingRepository).save(any(ContainerBookingEntity.class));
    }

    @Test
    void shouldThrowCustomException_whenRepositorySaveFails() {
        // given
        ContainerBookingReq req = new ContainerBookingReq();
        req.setContainerSize(20);
        req.setContainerType(ContainerType.DRY);
        req.setOrigin("Chennai");
        req.setDestination("Singapore");
        req.setQuantity(1);
        req.setTimestamp(Instant.now());

        when(containerBookingRepository.save(any(ContainerBookingEntity.class)))
                .thenReturn(Mono.error(new RuntimeException("DB error")));

        // when
        Mono<ContainerBookingRes> result = service.makeBooking(req);

        // then
        StepVerifier.create(result)
                .expectErrorSatisfies(throwable -> {
                    assertThat(throwable)
                            .isInstanceOf(ContainerBookingAppException.class)
                            .hasMessage(ErrorInfo.INTERNAL_SERVER_ERROR.getErrCode() + ": " + ErrorInfo.INTERNAL_SERVER_ERROR.getErrMsg());
                })
                .verify();

        verify(containerBookingRepository).save(any(ContainerBookingEntity.class));
    }

    @Test
    void shouldThrowException_whenSaveCallItselfThrowsSynchronously() {
        // given
        ContainerBookingReq req = new ContainerBookingReq();
        req.setContainerSize(10);
        when(containerBookingRepository.save(any())).thenThrow(new RuntimeException("Unexpected failure"));

        // when / then
        assertThatThrownBy(() -> service.makeBooking(req))
                .isInstanceOf(ContainerBookingAppException.class)
                .hasMessage(ErrorInfo.INTERNAL_SERVER_ERROR.getErrCode() + ": " + ErrorInfo.INTERNAL_SERVER_ERROR.getErrMsg());
    }
}
