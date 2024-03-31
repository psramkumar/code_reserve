package com.b2b.code.service;

import com.b2b.code.Exception.ApplicationExceptions;
import com.b2b.code.Exception.ApplicationExceptions.DeviceNeverBlockedException;
import com.b2b.code.Exception.ApplicationExceptions.DeviceNotAvailableException;
import com.b2b.code.Exception.ApplicationExceptions.UnauthorizedException;
import com.b2b.code.entity.Device;
import com.b2b.code.entity.DeviceAvailability;
import com.b2b.code.entity.DeviceReservation;
import com.b2b.code.repo.DeviceRepository;
import com.b2b.code.repo.DeviceReservationRepository;
import com.b2b.code.resp.DeviceDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    private final DeviceRepository repository;
    private final DeviceReservationRepository drRepository;

    @Override
    public Flux<DeviceDTO> findAll(PageRequest pg) {
        log.debug("Requesting DB call to get all Phones from Inventory..");
        return Flux.fromIterable(repository.findAll(pg).stream().map(Device::toDto).toList());
    }

    @Override
    public DeviceDTO reserveDeviceStatus(String code, String userId) {
        Optional<Device> deviceOp = repository.findById(code);
        Device d = deviceOp.orElseThrow(() -> new DeviceNotAvailableException("Device Not Available !"));

        if (d.getReservation() != null && d.getReservation().getAvailability() == DeviceAvailability.NOTAVAILABLE)
            throw new ApplicationExceptions.DeviceAlreadyBlockedException("Sorry! Device is Already Reserved..");

        DeviceReservation dr = DeviceReservation.builder()
                .deviceId(code)
                .bookedBy(userId)
                .bookedAt(LocalDateTime.now())
                .availability(DeviceAvailability.NOTAVAILABLE)
                .build();
        d.setReservation(dr);
        drRepository.saveAndFlush(dr);
        d = repository.saveAndFlush(d);
        return d.toDto();
    }

    @Override
    public DeviceDTO releaseDeviceStatus(String code, String userId) {
        Optional<Device> deviceOp = repository.findById(code);
        Device d = deviceOp.orElseThrow(() -> new DeviceNotAvailableException("Device Not Available !"));

        if (d.getReservation() == null || d.getReservation().getAvailability() == DeviceAvailability.AVAILABLE)
            throw new DeviceNeverBlockedException("Device is Still available for Reservation ...");

        if (!StringUtils.equalsAnyIgnoreCase(d.getReservation().getBookedBy(), userId))
            throw new UnauthorizedException("Device Registered with different User");

        DeviceReservation dr = DeviceReservation.builder()
                .deviceId(code)
                .bookedBy(null)
                .bookedAt(null)
                .availability(DeviceAvailability.AVAILABLE)
                .build();
        d.setReservation(dr);
        drRepository.saveAndFlush(dr);
        return d.toDto();
    }
}
