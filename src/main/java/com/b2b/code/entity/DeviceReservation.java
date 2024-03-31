package com.b2b.code.entity;

import com.b2b.code.resp.DeviceDTO;
import com.b2b.code.resp.Reservation;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceReservation {
    @Id
    private String deviceId;
    private String bookedBy;
    private LocalDateTime bookedAt;
    private DeviceAvailability availability;

    public Reservation toDto() {
        return Reservation.builder()
                .bookedAt(this.bookedAt)
                .bookedBy(this.bookedBy)
                .availability(this.availability)
                .build();
    }
}
