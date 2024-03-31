package com.b2b.code.resp;

import com.b2b.code.entity.DeviceAvailability;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record Reservation(
        String bookedBy,
        LocalDateTime bookedAt,
        DeviceAvailability availability
) {
}
