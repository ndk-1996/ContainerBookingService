package com.interview.maersk;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(
        title = "Container Booking Service",
        version = "1.0",
        description = "APIs for container bookings in a reactive WebFlux application"
))
@SpringBootApplication
public class ContainerBookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContainerBookingApplication.class, args);
    }
}
