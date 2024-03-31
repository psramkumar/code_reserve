package com.b2b.code.repo;

import com.b2b.code.entity.DeviceReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceReservationRepository extends JpaRepository<DeviceReservation, String> {
}
