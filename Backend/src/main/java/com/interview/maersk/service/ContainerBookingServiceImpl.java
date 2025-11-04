package com.interview.maersk.service;

import com.interview.maersk.constant.ErrorInfo;
import com.interview.maersk.dto.*;
import com.interview.maersk.entity.ContainerBookingEntity;
import com.interview.maersk.exception.ContainerBookingAppException;
import com.interview.maersk.repo.ContainerBookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
public class ContainerBookingServiceImpl implements BookingService {

    private final ContainerBookingRepository containerBookingRepository;
    private final WebClient webClient;
    private final AtomicLong bookingRef = new AtomicLong(957000001);
    private final Random random = new Random();

    private final Integer RANDOM_BOUND = 100;

    @Value("${external.retry.max-attempts}")
    private int externalApiMaxRetryAttempts;

    @Value("${database.retry.max-attempts}")
    private int databaseMaxRetryAttempts;

    @Autowired
    public ContainerBookingServiceImpl(ContainerBookingRepository containerBookingRepository, WebClient webClient) {
        this.containerBookingRepository = containerBookingRepository;
        this.webClient = webClient;
    }

    @Override
    public Mono<ContainerAvailabilityRes> checkAvailability(ContainerAvailabilityReq containerAvailabilityReq) {
        log.info("Checking availability for request: {} by hitting the configured non existing url", containerAvailabilityReq);
        int randomVal = random.nextInt(RANDOM_BOUND);

        // This is to mock the external API call response as the external API endpoint does not exist.
        Mono<ContainerSpaceRes> containerSpaceResMono = webClient.get()
                .uri("/checkAvailable")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> Mono.empty())
                .bodyToMono(ContainerSpaceRes.class)
                .onErrorReturn(ContainerSpaceRes.builder().availableSpace(randomVal).build())
                .defaultIfEmpty(ContainerSpaceRes.builder().availableSpace(randomVal).build())
                .retry(externalApiMaxRetryAttempts);


        return containerSpaceResMono.map(containerSpaceRes -> {
            boolean isAvailable = containerSpaceRes.getAvailableSpace() != 0;

            return ContainerAvailabilityRes.builder()
                    .available(isAvailable)
                    .build();
        });
    }

    @Override
    public Mono<ContainerBookingRes> makeBooking(ContainerBookingReq containerBookingReq) {
        log.info("Processing booking request: {}", containerBookingReq);
        long bookingRefVal = bookingRef.getAndIncrement();

        ContainerBookingEntity containerBookingEntity = ContainerBookingEntity.builder()
                .bookingRef(String.valueOf(bookingRefVal))
                .containerSize(containerBookingReq.getContainerSize())
                .containerType(containerBookingReq.getContainerType())
                .origin(containerBookingReq.getOrigin())
                .destination(containerBookingReq.getDestination())
                .quantity(containerBookingReq.getQuantity())
                .timestamp(containerBookingReq.getTimestamp())
                .build();

        try {
            Mono<ContainerBookingEntity> containerBookingEntityMono = containerBookingRepository.save(containerBookingEntity)
                    .retry(databaseMaxRetryAttempts);

            return containerBookingEntityMono.map(savedEntity -> ContainerBookingRes.builder()
                    .bookingRef(savedEntity.getBookingRef())
                    .build())
                    .onErrorMap(e -> {
                        log.error("Error while saving booking entity", e);
                        return new ContainerBookingAppException(ErrorInfo.INTERNAL_SERVER_ERROR, e);
                    });
        } catch (Exception e) {
            log.error("Error while saving booking entity", e);
            throw new ContainerBookingAppException(ErrorInfo.INTERNAL_SERVER_ERROR, e);
        }
    }
}
