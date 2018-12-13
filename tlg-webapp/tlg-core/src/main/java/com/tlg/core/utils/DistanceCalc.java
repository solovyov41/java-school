package com.tlg.core.utils;

import com.tlg.core.entity.Waypoint;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class DistanceCalc implements DistanceCalculator {

    /**
     * Calculates the distance in km between two lat/long points
     * using the haversine formula
     */
    @Override
    public double calculate(double lat1, double lng1, double lat2, double lng2) {
        int r = 6371; // average radius of the earth in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                        * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return r * c;
    }

    @Override
    public double calculate(List<Waypoint> route) {
        route.sort(Comparator.comparing(Waypoint::getPosition));

        double result = 0;
        for (int i = 0, l = route.size() - 1; i < l; i++) {
            result += calculate(route.get(i).getCity().getLatitude(), route.get(i).getCity().getLongitude(),
                    route.get(i + 1).getCity().getLatitude(), route.get(i + 1).getCity().getLongitude());
        }
        return result;
    }

}