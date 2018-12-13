package com.tlg.core.utils;

import com.tlg.core.dto.CarriageDto;
import com.tlg.core.entity.Cargo;
import com.tlg.core.entity.Carriage;
import com.tlg.core.entity.Waypoint;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Component
public class OrderWeightCalc implements MaxOrderWeightCalc {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public BigDecimal calculate(Carriage carriage) {
        int maxWeight = 0;
        int currentWeight = 0;
        List<Cargo> cargoes = carriage.getCargoes();
        List<Waypoint> route = carriage.getWaypoints();
        route.sort(Comparator.comparing(Waypoint::getPosition));
        for (int i = 0, l = route.size(); i < l; i++) {
            for (Cargo cargo : cargoes) {
                if (cargo.getDepartureWaypoint().getPosition().equals(route.get(i).getPosition())) {
                    currentWeight += cargo.getWeight();
                }
                if (cargo.getDestinationWaypoint().getPosition().equals(route.get(i).getPosition())) {
                    currentWeight -= cargo.getWeight();
                }
            }
            if (currentWeight > maxWeight) {
                maxWeight = currentWeight;
            }
        }
        return BigDecimal.valueOf(maxWeight, 3);
    }

    @Override
    public BigDecimal calculate(CarriageDto carriageDto) {
        return calculate(modelMapper.map(carriageDto, Carriage.class));
    }
}
