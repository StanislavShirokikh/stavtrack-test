package ru.stavtrack.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "fleet-stack.service")
@Data
public class FleetStackServiceProperties {
    private String apiUrl;
    private String apiKey;
}
