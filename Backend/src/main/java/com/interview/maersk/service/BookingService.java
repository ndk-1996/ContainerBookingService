package com.interview.maersk.service;

import com.interview.maersk.dto.ContainerAvailabilityReq;
import com.interview.maersk.dto.ContainerAvailabilityRes;
import com.interview.maersk.dto.ContainerBookingReq;
import com.interview.maersk.dto.ContainerBookingRes;
import reactor.core.publisher.Mono;

public interface BookingService {

    Mono<ContainerAvailabilityRes> checkAvailability(ContainerAvailabilityReq containerAvailabilityReq);

    Mono<ContainerBookingRes> makeBooking(ContainerBookingReq containerBookingReq);
}
