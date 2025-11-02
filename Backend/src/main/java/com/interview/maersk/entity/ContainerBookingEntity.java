package com.interview.maersk.entity;

import com.interview.maersk.constant.ContainerType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Document(collection = "bookings")
public class ContainerBookingEntity {

    @MongoId
    @Field("booking_ref")
    private String bookingRef;

    @Field("container_size")
    private Integer containerSize;

    @Field("container_type")
    private ContainerType containerType;

    @Field("origin")
    private String origin;

    @Field("destination")
    private String destination;

    @Field("quantity")
    private Integer quantity;

    @Field("timestamp")
    private Instant timestamp;
}
