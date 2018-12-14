package com.tlg.util;

import com.tlg.dto.CargoDto;
import com.tlg.dto.CarriageDto;
import com.tlg.dto.DriverDto;
import com.tlg.dto.WaypointDto;
import com.tlg.model.Cargo;
import com.tlg.model.Carriage;
import com.tlg.model.Driver;
import com.tlg.model.Waypoint;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.json.bind.JsonbConfig;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * This class uses CDI to alias Java EE resources, such as the persistence context, to CDI beans
 */
public class Resources {

    @Produces
    @PersistenceContext
    private EntityManager em;

    @Produces
    public Logger produceLog(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
    }

    @Produces
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(WaypointDto.class, Waypoint.class)
                .addMappings(mapper -> mapper.skip(Waypoint::setId))
                .addMappings(mapper -> mapper.skip(Waypoint::setCarriage))
                .addMappings(mapper -> mapper.map(source -> source.getCity().getName(), Waypoint::setCity));
        modelMapper.createTypeMap(CargoDto.class, Cargo.class)
                .addMappings(mapper -> mapper.skip(Cargo::setId))
                .addMappings(mapper -> mapper.skip(Cargo::setCarriage))
                .addMappings(mapper -> mapper.map(source -> source.getDepartureWaypoint().getCity().getName(), Cargo::setDepartureCity))
                .addMappings(mapper -> mapper.map(source -> source.getDestinationWaypoint().getCity().getName(), Cargo::setDestinationCity));
        modelMapper.createTypeMap(DriverDto.class, Driver.class)
                .addMappings(mapper -> mapper.skip(Driver::setId))
                .addMappings(mapper -> mapper.skip(Driver::setCarriage))
                .addMappings(mapper -> mapper.map(source -> source.getUser().getName(),Driver::setName))
                .addMappings(mapper -> mapper.map(source -> source.getUser().getSurname(),Driver::setSurname));
        modelMapper.createTypeMap(CarriageDto.class, Carriage.class)
                .addMappings(mapper -> mapper.skip(Carriage::setId));
        modelMapper.validate();
        return modelMapper;
    }

    @Produces
    public JsonbConfig getJsonConfig(){
        return new JsonbConfig().withDateFormat("yyyy-MM-dd HH:mm", null);
    }
}
