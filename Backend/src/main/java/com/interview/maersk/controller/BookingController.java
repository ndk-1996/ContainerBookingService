package com.interview.maersk.controller;

import com.interview.maersk.dto.ContainerAvailabilityReq;
import com.interview.maersk.dto.ContainerAvailabilityRes;
import com.interview.maersk.dto.ContainerBookingReq;
import com.interview.maersk.dto.ContainerBookingRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

public interface BookingController {

    Mono<ResponseEntity<ContainerAvailabilityRes>> checkAvailability(@RequestBody ContainerAvailabilityReq containerAvailabilityReq);

    Mono<ResponseEntity<ContainerBookingRes>> makeBooking(@RequestBody ContainerBookingReq containerBookingReq);
}
