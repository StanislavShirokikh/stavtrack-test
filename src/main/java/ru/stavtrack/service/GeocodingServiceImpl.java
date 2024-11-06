package ru.stavtrack;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeocodingServiceImpl implements GeocodingService {
    private final RestTemplate restTemplate;
    private final FleetStackServiceProperties properties;

    @Override
    public GeocodingResponse getAddressesAndDistance(double[] startPos, double[] endPos) {
        String startAddress = getAddressFromCoordinates(startPos);
        String endAddress = getAddressFromCoordinates(endPos);
        double distance = calculateDistance(startPos, endPos);

        return new GeocodingResponse(startPos, endPos, startAddress, endAddress, distance);
    }

    private String getAddressFromCoordinates(double[] position) {
        String finalUrl = getFinalUrl(properties.getApiUrl());

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(finalUrl)
                .queryParam("lat", position[0])
                .queryParam("lon", position[1])
                .queryParam("key", properties.getApiKey());

        try {
            Map<String, Object> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {
                    }
            ).getBody();

            if (response == null || !response.containsKey("address")) {
                throw new AddressNotFoundException("Address not found for coordinates: " + Arrays.toString(position));
            }

            return (String) response.get("address");
        } catch (RestClientException e) {
            throw new AddressNotFoundException("Failed to retrieve address for coordinates: " + Arrays.toString(position), e);
        }
    }

    private double calculateDistance(double[] startPos, double[] endPos) {
        final int EARTH_RADIUS = 6371000; // Радиус Земли в метрах
        double latDistance = Math.toRadians(endPos[0] - startPos[0]);
        double lonDistance = Math.toRadians(endPos[1] - startPos[1]);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(startPos[0])) * Math.cos(Math.toRadians(endPos[0]))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    private String getFinalUrl(String url) {
        return url + "/api/getaddress";
    }
}
