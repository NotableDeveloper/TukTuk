package com.example.tuktuk.stadium.util;

import com.example.tuktuk.stadium.domain.Location;

public class LocationToStringConverter {

    public static String DELIMITER = " ";

    public static String convertLocationToString(Location location) {
        StringBuilder sb = new StringBuilder();
        sb.append(location.getProvince()).append(DELIMITER).
                append(location.getCity()).append(DELIMITER).
                append(location.getRoadNameAddress());
        return sb.toString();
    }
}
