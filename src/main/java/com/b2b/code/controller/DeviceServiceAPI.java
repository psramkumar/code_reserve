package com.b2b.code.controller;

import com.b2b.code.resp.ErrorResponse;
import com.b2b.code.resp.DeviceDTO;
import com.b2b.code.resp.DeviceMetaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/api/v1/devices")
@Tag(name = "Device Reservation")
public interface DeviceServiceAPI {

    @GetMapping
    @Operation(description = "Get List of Devices")
    @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DeviceDTO.class)))})
    ResponseEntity<Flux<DeviceDTO>> listDevicesApi(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size,
            @Parameter(hidden = true) @RequestHeader("X-AUTH-USERID") String userId);

    @PatchMapping("/{code}/reserve")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Reserved",        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DeviceDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad response",    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Data Not Found",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    ResponseEntity<Mono<DeviceDTO>> reserveDevicesApi(@PathVariable String code, @Parameter(hidden = true) @RequestHeader("X-AUTH-USERID") String userId);

    @PatchMapping("/{code}/release") // the base path will be derived from Application.yml
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Released",        content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DeviceDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Bad response",    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Data Not Found",  content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
    })
    ResponseEntity<Mono<DeviceDTO>> releaseDevicesApi(@PathVariable String code, @Parameter(hidden = true) @RequestHeader("X-AUTH-USERID") String userId);

    @GetMapping("/fonosync")
    @Operation(description = "Get List of Device Meta from FONO API")
    @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = DeviceMetaResponse.class)))})
    ResponseEntity<Flux<DeviceMetaResponse>> listDevicesFoneApi(@RequestParam String name, @Parameter(hidden = true) @RequestHeader("X-AUTH-USERID") String userId);
}
