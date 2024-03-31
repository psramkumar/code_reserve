package com.b2b.code.service;

import com.b2b.code.Exception.ApplicationExceptions;
import com.b2b.code.entity.Device;
import com.b2b.code.entity.DeviceAvailability;
import com.b2b.code.entity.DeviceReservation;
import com.b2b.code.repo.DeviceRepository;
import com.b2b.code.repo.DeviceReservationRepository;
import com.b2b.code.resp.DeviceDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeviceServiceImplTest {
    @InjectMocks
    private DeviceServiceImpl deviceService;
    @Mock
    private DeviceRepository repository;

    @Test
    public void testGetAllPhones() {
        when(repository.findAll(PageRequest.of(0, 10)))
                .thenReturn(new PageImpl<>(List.of(
                        Device.builder().build(),
                        Device.builder().build())));
        Flux<DeviceDTO> devices = deviceService.findAll(PageRequest.of(0, 10));
        assertEquals(2, Objects.requireNonNull(devices.collectList().block()).size());
    }

    @Test
    public void test_when_a_user_try_to_Release_Device_Status() {
        String id = UUID.randomUUID().toString();
        String userId = "1234";
        Device device = new Device();
        device.setId(id);
        device.setName("Test Model");

        //1. when Device never Reserved.
        when(repository.findById(id)).thenReturn(Optional.of(device));
        assertThrows(ApplicationExceptions.DeviceNeverBlockedException.class,
                () -> deviceService.releaseDeviceStatus(id, userId), "Device is Still available for Reservation ...");

        //2. when Device reserved Before and released
        device.setReservation(DeviceReservation.builder().deviceId(id).bookedBy(userId).availability(DeviceAvailability.AVAILABLE).build());
        when(repository.findById(id)).thenReturn(Optional.of(device));
        assertThrows(ApplicationExceptions.DeviceNeverBlockedException.class,
                () -> deviceService.releaseDeviceStatus(id, userId), "Device is Still available for Reservation ...");

        //3. when another User Attempt to Release
        device.setReservation(DeviceReservation.builder().deviceId(id).bookedBy(userId).availability(DeviceAvailability.NOTAVAILABLE).build());
        assertThrows(ApplicationExceptions.UnauthorizedException.class,
                () -> deviceService.releaseDeviceStatus(id, "3333"), "Device Registered with different User");

        //When Successfully Released.
        DeviceDTO deviceDTO = deviceService.releaseDeviceStatus(id, userId);
        Assertions.assertEquals(deviceDTO.reservation().availability(), DeviceAvailability.AVAILABLE);

    }
}
