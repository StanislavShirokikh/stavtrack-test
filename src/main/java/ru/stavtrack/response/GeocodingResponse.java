package ru.stavtrack;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GeocodingResponse {
    private double[] startPos;
    private double[] endPos;
    private String startAddress;
    private String endAddress;
    private double distance;
}
