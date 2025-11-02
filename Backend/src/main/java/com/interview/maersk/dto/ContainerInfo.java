package com.interview.maersk.dto;

import com.interview.maersk.constant.ContainerType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class ContainerInfo {

    @NotNull(message = "ContainerSize is required.")
    private Integer containerSize;

    @NotNull(message = "ContainerType is required.")
    private ContainerType containerType;

    @NotBlank(message = "Origin is required.")
    @Size(min = 5, max = 20, message = "Origin must be between 5 and 20 characters.")
    private String origin;

    @NotBlank(message = "Destination is required.")
    @Size(min = 5, max = 20, message = "Destination must be between 5 and 20 characters.")
    private String destination;

    @NotNull(message = "Quantity is required.")
    @Min(value = 1, message = "Quantity must be at least 1,")
    @Max(value = 100, message = "Quantity cannot exceed 100.")
    private Integer quantity;
}
