package com.interview.maersk.controller;

import com.interview.maersk.dto.ContainerAvailabilityReq;
import com.interview.maersk.dto.ContainerAvailabilityRes;
import com.interview.maersk.dto.ContainerBookingReq;
import com.interview.maersk.dto.ContainerBookingRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@Tag(name = "Container booking API", description = "Endpoints for container booking operations")
public interface BookingController {

    @Operation(summary = "Check availability for a type of container")
    @PostMapping("/container/availability")
    Mono<ResponseEntity<ContainerAvailabilityRes>> checkAvailability(@Valid @RequestBody ContainerAvailabilityReq containerAvailabilityReq);

    @Operation(summary = "Make a booking for a type of container")
    @PostMapping("/container")
    Mono<ResponseEntity<ContainerBookingRes>> makeBooking(@Valid @RequestBody ContainerBookingReq containerBookingReq);
}
