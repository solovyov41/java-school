package com.tlg.core.utils;

import com.tlg.core.entity.Waypoint;

import java.util.List;

public interface DistanceCalculator {

    double calculate(double lat1, double lng1, double lat2, double lng2);

    double calculate(List<Waypoint> route);

}

