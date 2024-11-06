package ru.stavtrack;

public interface GeocodingService {
    GeocodingResponse getAddressesAndDistance(double[] startPos, double[] endPos);
}
