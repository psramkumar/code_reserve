package com.b2b.code.service;

import com.b2b.code.resp.DeviceDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public interface DeviceService {
    Flux<DeviceDTO> findAll(PageRequest pg);
    DeviceDTO reserveDeviceStatus(String code, String userId);
    DeviceDTO releaseDeviceStatus(String code, String userId);
}
