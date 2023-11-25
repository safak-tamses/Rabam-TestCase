package com.example.dockerversion.service;

import com.example.dockerversion.model.DTO.AddressCoordinate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Service
public class SideService {
    private static final Logger logger = LogManager.getLogger(CourierService.class);

    public AddressCoordinate findCoordinate(String address) throws IOException {
        String encodedAddress = URLEncoder.encode(address, "UTF-8");

        String apiUrl = "https://nominatim.openstreetmap.org/search?format=json&q=" + encodedAddress;

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        String latitude = extractValueFromJson(response.toString(), "lat");
        String longitude = extractValueFromJson(response.toString(), "lon");


        connection.disconnect();

        Double lat = Double.parseDouble(latitude.replaceAll("\"", ""));
        Double lon = Double.parseDouble(longitude.replaceAll("\"", ""));

        AddressCoordinate coordinate = new AddressCoordinate(lon, lat);
        return coordinate;
    }

    public double haversine(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371.0;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = R * c;
        //km cinsinden mesafeyi verir
        return distance;
    }

    private static String extractValueFromJson(String json, String key) {
        int startIndex = json.indexOf("\"" + key + "\":") + key.length() + 3;
        int endIndex = json.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = json.indexOf("}", startIndex);
        }
        return json.substring(startIndex, endIndex);
    }
}
