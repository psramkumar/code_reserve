package com.b2b.code.service;

import com.b2b.code.repo.DeviceRepository;
import com.b2b.code.resp.DeviceMetaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class FonoSyncService {
    private final DeviceRepository repository;

    @Value("${fonoapi.token}")
    private String fonoApiToken;

    @Value("${fonoapi.baseurl}")
    private String baseUrl;

    /**
     * Scheduling to Sync Device Meta Data from FonoAPI.
     */
    @Scheduled(cron = "${fonoapi.corn}")
    public void syncFoneAppData() {
        repository.findAll().parallelStream().forEach(it -> {
            Flux<DeviceMetaResponse> deviceMetaResp = this.getDeviceInfo(it.getName());
            DeviceMetaResponse dm = deviceMetaResp.blockFirst();
            if (dm != null) {
                it.setBand_2g(dm._2g_bands());
                it.setBand_3g(dm._3g_bands());
                it.setBand_4g(dm._4g_bands());
                it.setTechnology(dm.technology());
                repository.saveAndFlush(it);
            }
        });
    }

    public Flux<DeviceMetaResponse> getDeviceInfo(String deviceName) {
        WebClient.ResponseSpec req = WebClient.create(baseUrl).post()
                .uri(ub -> ub.path("/v1/getdevice")
                        .queryParam("device", deviceName)
                        .queryParam("token", fonoApiToken)
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("")
                .retrieve();
        return req.bodyToFlux(DeviceMetaResponse.class);
    }
}
