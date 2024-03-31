package com.b2b.code.controller;

import com.b2b.code.resp.Post;
import com.b2b.code.service.FonoSyncService;
import com.b2b.code.resp.DeviceDTO;
import com.b2b.code.resp.DeviceMetaResponse;
import com.b2b.code.service.DeviceService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class DeviceServiceController implements DeviceServiceAPI {

    private final DeviceService deviceService;
    private final FonoSyncService fService;

    @Override
    public ResponseEntity<Flux<DeviceDTO>> listDevicesApi(int page, int size, String userId) {
        return ResponseEntity.ok(deviceService.findAll(PageRequest.of(page, size)));
    }

    @Override
    public ResponseEntity<Mono<DeviceDTO>> reserveDevicesApi(String code, String userId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Mono.just(deviceService.reserveDeviceStatus(code, userId)));
    }

    @Override
    public ResponseEntity<Mono<DeviceDTO>> releaseDevicesApi(String code, String userId) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(Mono.just(deviceService.releaseDeviceStatus(code, userId)));
    }

    @Override
    public ResponseEntity<Flux<DeviceMetaResponse>> listDevicesFoneApi(String name, String userId) {
        return ResponseEntity.ok(fService.getDeviceInfo(name));
    }

    //For Testing the WebClient since FonoAPI is nt working
    @GetMapping("posts")
    @Hidden
    public Flux<Post> listObjects() {
        return WebClient.create("https://jsonplaceholder.typicode.com")
                .get()
                .uri("/posts")
                .retrieve()
                .bodyToFlux(Post.class);
    }
}
