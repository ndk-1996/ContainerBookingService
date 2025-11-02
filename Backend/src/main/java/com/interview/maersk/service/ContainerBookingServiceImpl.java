package com.interview.maersk.service;

import com.interview.maersk.dto.ContainerAvailabilityReq;
import com.interview.maersk.dto.ContainerAvailabilityRes;
import com.interview.maersk.dto.ContainerBookingReq;
import com.interview.maersk.dto.ContainerBookingRes;
import com.interview.maersk.entity.ContainerBookingEntity;
import com.interview.maersk.repo.ContainerBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class ContainerBookingServiceImpl implements BookingService {

    private final ContainerBookingRepository containerBookingRepository;
    private final AtomicLong bookingRef = new AtomicLong(957000001);

    @Autowired
    public ContainerBookingServiceImpl(ContainerBookingRepository containerBookingRepository) {
        this.containerBookingRepository = containerBookingRepository;
    }

    @Override
    public Mono<ContainerAvailabilityRes> checkAvailability(ContainerAvailabilityReq containerAvailabilityReq) {
        return null;
    }

    @Override
    public Mono<ContainerBookingRes> makeBooking(ContainerBookingReq containerBookingReq) {
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

        Mono<ContainerBookingEntity> containerBookingEntityMono = containerBookingRepository.save(containerBookingEntity);

        return containerBookingEntityMono.map(savedEntity -> ContainerBookingRes.builder()
                .bookingRef(savedEntity.getBookingRef())
                .build());
    }
}
