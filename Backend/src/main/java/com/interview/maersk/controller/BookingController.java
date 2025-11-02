package com.interview.maersk.controller;

import com.interview.maersk.dto.ContainerAvailabilityReq;
import com.interview.maersk.dto.ContainerAvailabilityRes;
import com.interview.maersk.dto.ContainerBookingReq;
import com.interview.maersk.dto.ContainerBookingRes;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

public interface BookingController {

    @PostMapping("/container/availability")
    Mono<ResponseEntity<ContainerAvailabilityRes>> checkAvailability(@Valid @RequestBody ContainerAvailabilityReq containerAvailabilityReq);

    @PostMapping("/container")
    Mono<ResponseEntity<ContainerBookingRes>> makeBooking(@Valid @RequestBody ContainerBookingReq containerBookingReq);
}
