package com.b2b.code.resp;

import com.b2b.code.entity.DeviceReservation;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record DeviceDTO(
        @Schema(description = "Unique code to identify the Device",
                example = "uid",
                requiredMode = Schema.RequiredMode.REQUIRED,
                title = "Device Code"
        )
        String code,
        @Schema(description = "Human Readable Device Name",
                example = "Meaningful Name",
                requiredMode = Schema.RequiredMode.REQUIRED,
                title = "Device Name"
        )
        String name,
        String technology,
        String band_2g,
        String band_3g,
        String band_4g,
        Reservation reservation) {
}
