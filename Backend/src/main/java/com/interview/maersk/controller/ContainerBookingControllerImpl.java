package com.interview.maersk.controller;

import com.interview.maersk.dto.ContainerAvailabilityReq;
import com.interview.maersk.dto.ContainerAvailabilityRes;
import com.interview.maersk.dto.ContainerBookingReq;
import com.interview.maersk.dto.ContainerBookingRes;
import com.interview.maersk.service.BookingService;
import com.interview.maersk.validator.ContainerBookingValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/bookings")
@Slf4j
public class ContainerBookingControllerImpl implements BookingController {

    private final ContainerBookingValidator containerBookingValidator;
    private final BookingService bookingService;

    @Autowired
    public ContainerBookingControllerImpl(ContainerBookingValidator containerBookingValidator, BookingService bookingService) {
        this.containerBookingValidator = containerBookingValidator;
        this.bookingService = bookingService;
    }


    @Override
    public Mono<ResponseEntity<ContainerAvailabilityRes>> checkAvailability(ContainerAvailabilityReq containerAvailabilityReq) {
        log.info("Received availability request: {}", containerAvailabilityReq);
        containerBookingValidator.validateContainerSize(containerAvailabilityReq.getContainerSize());
        Mono<ContainerAvailabilityRes> containerAvailabilityResMono = bookingService.checkAvailability(containerAvailabilityReq);

        return containerAvailabilityResMono.map(ResponseEntity::ok);
    }

    @Override
    public Mono<ResponseEntity<ContainerBookingRes>> makeBooking(ContainerBookingReq containerBookingReq) {
        log.info("Received booking request: {}", containerBookingReq);
        containerBookingValidator.validateContainerSize(containerBookingReq.getContainerSize());
        Mono<ContainerBookingRes> containerBookingResMono = bookingService.makeBooking(containerBookingReq);

        return containerBookingResMono.map(ResponseEntity::ok);
    }
}
