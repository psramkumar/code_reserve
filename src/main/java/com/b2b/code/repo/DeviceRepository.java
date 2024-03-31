package com.b2b.code.repo;

import com.b2b.code.entity.Device;
import jakarta.persistence.LockModeType;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, String> {

    @Override
    Page<Device> findAll(Pageable pageable);

    /**
     * Retrieves a Device by its id with a pessimistic write lock.
     *
     * This method is designed to be used in scenarios where you want to obtain a lock on the
     * retrieved entity to prevent concurrent updates by other transactions.
     *
     * @param id The ID of the device for which to retrieve the reservation entity.
     * @return The DeviceReservationEntity associated with the provided deviceId, or null if not found.
     */
    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Device> findById(@NotNull String id);
}
