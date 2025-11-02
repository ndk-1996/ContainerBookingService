package com.interview.maersk.controller;

import com.interview.maersk.dto.ContainerAvailabilityReq;
import com.interview.maersk.dto.ContainerAvailabilityRes;
import com.interview.maersk.dto.ContainerBookingReq;
import com.interview.maersk.dto.ContainerBookingRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/bookings")
public class ContainerBookingControllerImpl implements BookingController {


    @Override
    public Mono<ResponseEntity<ContainerAvailabilityRes>> checkAvailability(ContainerAvailabilityReq containerAvailabilityReq) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<ContainerBookingRes>> makeBooking(ContainerBookingReq containerBookingReq) {
        return null;
    }
}
