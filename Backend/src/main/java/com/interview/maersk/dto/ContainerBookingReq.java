package com.interview.maersk.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class ContainerBookingReq extends ContainerInfo {

    @NotNull(message = "Timestamp is required.")
    private Instant timestamp;
}
