package ru.stavtrack;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GeocodingRequest {
    @JsonProperty("startPos")
    private double[] startPos;
    @JsonProperty("endPos")
    private double[] endPos;
}
