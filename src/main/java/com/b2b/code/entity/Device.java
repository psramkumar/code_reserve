package com.b2b.code.entity;

import com.b2b.code.resp.DeviceDTO;
import com.b2b.code.resp.Reservation;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    @Id
    @jakarta.persistence.Id
    private String id;
    private String name;
    private String technology;
    private String band_2g;
    private String band_3g;
    private String band_4g;
    @OneToOne
    private DeviceReservation reservation;

    public DeviceDTO toDto() {
        DeviceDTO.DeviceDTOBuilder b = DeviceDTO.builder();
        b.code(this.id)
                .name(this.name)
                .technology(this.technology)
                .band_2g(this.band_2g)
                .band_3g(this.band_3g)
                .band_4g(this.band_4g)
                .reservation(this.reservation == null ? Reservation.builder().availability(DeviceAvailability.AVAILABLE).build() : this.reservation.toDto());
        return b.build();
    }
}
