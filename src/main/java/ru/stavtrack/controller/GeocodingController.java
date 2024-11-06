package ru.stavtrack;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/geocoding")
@RequiredArgsConstructor
public class GeocodingController {
    private final GeocodingService geocodingService;

    @PostMapping("/response")
    public GeocodingResponse getAddressesAndDistance(@RequestBody GeocodingRequest request) {
        return geocodingService.getAddressesAndDistance(request.getStartPos(), request.getEndPos());
    }
}
