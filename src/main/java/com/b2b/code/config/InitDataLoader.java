package com.b2b.code.config;

import com.b2b.code.entity.Device;
import com.b2b.code.repo.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitDataLoader implements CommandLineRunner {
    private final DeviceRepository repository;

    @Override
    public void run(String... args) throws Exception {
        if (0 == repository.count()) {
            log.info("Executing DataLoad first Time");
            List<Device> devices = List.of(
                    Device.builder().id("58d8fac8-b815-48c0-a03b-81ec48be160f")
                            .name("Samsung Galaxy S9")
                            .technology("4G")
                            .band_2g("2G Bands")
                            .band_3g("3G Bands")
                            .band_4g("4G Bands")
                            .build(),
                    Device.builder().id(UUID.randomUUID().toString())
                            .name("Samsung Galaxy S8")
                            .technology("4G")
                            .band_2g("2G Bands")
                            .band_3g("3G Bands")
                            .band_4g("4G Bands")
                            .build(),
                    Device.builder().id(UUID.randomUUID().toString())
                            .name("Samsung Galaxy S8")
                            .technology("4G")
                            .band_2g("2G Bands")
                            .band_3g("3G Bands")
                            .band_4g("4G Bands")
                            .build(),
                    Device.builder().id(UUID.randomUUID().toString())
                            .name("Motorola Nexus 6")
                            .technology("4G")
                            .band_2g("2G Bands")
                            .band_3g("3G Bands")
                            .band_4g("4G Bands")
                            .build(),
                    Device.builder().id(UUID.randomUUID().toString())
                            .name("Oneplus 9")
                            .technology("5G")
                            .band_2g("2G Bands")
                            .band_3g("3G Bands")
                            .band_4g("5G Bands")
                            .build(),
                    Device.builder().id(UUID.randomUUID().toString())
                            .name("Apple iPhone 13")
                            .technology("5G")
                            .band_2g("2G Bands")
                            .band_3g("3G Bands")
                            .band_4g("5G Bands")
                            .build(),
                    Device.builder().id(UUID.randomUUID().toString())
                            .name("Apple iPhone 12")
                            .technology("5G")
                            .band_2g("2G Bands")
                            .band_3g("3G Bands")
                            .band_4g("5G Bands")
                            .build(),
                    Device.builder().id(UUID.randomUUID().toString())
                            .name("Apple iPhone 11")
                            .technology("4G")
                            .band_2g("2G Bands")
                            .band_3g("3G Bands")
                            .band_4g("4G Bands")
                            .build(),
                    Device.builder().id(UUID.randomUUID().toString())
                            .name("iPhone X")
                            .technology("4G")
                            .band_2g("2G Bands")
                            .band_3g("3G Bands")
                            .band_4g("4G Bands")
                            .build(),
                    Device.builder().id(UUID.randomUUID().toString())
                            .name("Nokia 3310")
                            .technology("2G")
                            .band_2g("2G Bands")
                            .band_3g(null)
                            .band_4g(null)
                            .build());
            this.repository.saveAllAndFlush(devices);
        }
    }

}
