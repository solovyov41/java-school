package com.tlg.core.config;

import com.tlg.core.dto.*;
import com.tlg.core.entity.*;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

import static org.modelmapper.Conditions.isNotNull;

@Configuration
public class CoreMappers {

    @Bean
    public ModelMapper getModelMapper() {

        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration() // Fetch the configuration
//                .setFieldMatchingEnabled(true)  // Enables field matching
//                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE) // Enables PRIVATE field access level
//                .setMatchingStrategy(MatchingStrategies.STRICT)
//        ;
        return modelMapper;
    }

    /**
     * User to User mapping config
     *
     * @return type map user -> user
     */
    @Bean
    public TypeMap<User, User> getUserUserTypeMap() {
        return getModelMapper().createTypeMap(User.class, User.class)
                .addMappings(mapper -> mapper.skip(User::setId))
                .addMappings(mapper -> mapper.skip(User::setPassword))
                .addMappings(mapper -> mapper.when(isNotNull()).map(User::getRole, User::setRole))
                .addMappings(mapper -> mapper.skip(User::setCreateDate))
                .addMappings(mapper -> mapper.skip(User::setUpdateDate))
                .addMappings(mapper -> mapper.when(isNotNull()).map(User::getAvatar, User::setAvatar));
    }

    /**
     * Cargo to Cargo mapping config
     *
     * @return type map Cargo -> Cargo
     */
    @Bean
    public TypeMap<Cargo, Cargo> getCargoCargoTypeMap() {
        return getModelMapper().createTypeMap(Cargo.class, Cargo.class)
                .addMappings(mapper -> mapper.skip(Cargo::setId))
                .addMappings(mapper -> mapper.skip(Cargo::setNumber))
                .addMappings(mapper -> mapper.skip(Cargo::setCreateDate))
                .addMappings(mapper -> mapper.skip(Cargo::setUpdateDate))

                .addMappings(mapper -> mapper.when(isNotNull()).map(Cargo::getName, Cargo::setName))
                .addMappings(mapper -> mapper.when(isNotNull()).map(Cargo::getState, Cargo::setState))
                .addMappings(mapper -> mapper.when(isNotNull()).map(Cargo::getWeight, Cargo::setWeight))
                .addMappings(mapper -> mapper.when(isNotNull()).map(Cargo::getDescription, Cargo::setDescription))

                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(Cargo::getDepartureWaypoint, Cargo::setDepartureWaypoint))
                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(Cargo::getDestinationWaypoint, Cargo::setDestinationWaypoint))
                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(Cargo::getCarriage, Cargo::setCarriage));
    }

    /**
     * CarriageDto to Carriage mapping config
     *
     * @return type map CarriageDto -> Carriage
     */
    @Bean
    public TypeMap<CarriageDto, Carriage> getCarriageDtoCarriageTypeMap() {
        return getModelMapper().createTypeMap(CarriageDto.class, Carriage.class, "CarriageDtoCarriage")
                .addMappings(mapper -> mapper.when(isNotNull()).map(CarriageDto::getUniqueNumber, Carriage::setUniqueNumber))
                .addMappings(mapper -> mapper.when(isNotNull()).map(CarriageDto::getStatus, Carriage::setStatus))
                .addMappings(mapper -> mapper.when(isNotNull()).map(CarriageDto::getCustomerName, Carriage::setCustomerName))
                .addMappings(mapper -> mapper.when(isNotNull()).map(CarriageDto::getInitiateDate, Carriage::setInitiateDate))
                .addMappings(mapper -> mapper.when(isNotNull()).map(CarriageDto::getFinishDate, Carriage::setFinishDate))
                .addMappings(mapper -> mapper.when(isNotNull()).map(CarriageDto::getEstimatedLeadTime, Carriage::setEstimatedLeadTime))

                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(CarriageDto::getWaypoints, Carriage::setWaypoints))
                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(CarriageDto::getVehicle, Carriage::setVehicle))
                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(CarriageDto::getDrivers, Carriage::setDrivers))
                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(CarriageDto::getCargoes, Carriage::setCargoes));
    }

    /**
     * CargoDto to Cargo mapping config
     *
     * @return type map CargoDto -> Cargo
     */
    @Bean
    public TypeMap<CargoDto, Cargo> getCargoDtoCargoTypeMap() {
        return getModelMapper().createTypeMap(CargoDto.class, Cargo.class, "CargoDtoCargo")
                .addMappings(mapper -> mapper.when(isNotNull()).map(CargoDto::getNumber, Cargo::setNumber))
                .addMappings(mapper -> mapper.when(isNotNull()).map(CargoDto::getName, Cargo::setName))
                .addMappings(mapper -> mapper.when(isNotNull()).map(CargoDto::getDescription, Cargo::setDescription))
                .addMappings(mapper -> mapper.when(isNotNull()).map(CargoDto::getState, Cargo::setState))
                .addMappings(mapper -> mapper.when(isNotNull()).map(CargoDto::getWeight, Cargo::setWeight))

                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(CargoDto::getCarriage, Cargo::setCarriage))
                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(CargoDto::getDepartureWaypoint, Cargo::setDepartureWaypoint))
                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(CargoDto::getDestinationWaypoint, Cargo::setDestinationWaypoint));
    }


    /**
     * VehicleDto to Vehicle mapping config
     *
     * @return type map vehicleDto -> vehicle
     */
    @Bean
    public TypeMap<VehicleDto, Vehicle> getVehicleDtoVehicleTypeMap() {
        return getModelMapper().createTypeMap(VehicleDto.class, Vehicle.class)
                .addMappings(mapper -> mapper.when(isNotNull()).map(VehicleDto::getLicPlateNum, Vehicle::setLicPlateNum))
                .addMappings(mapper -> mapper.when(isNotNull()).map(VehicleDto::getState, Vehicle::setState))
                .addMappings(mapper -> mapper.when(isNotNull()).map(VehicleDto::getPassSeatsNum, Vehicle::setPassSeatsNum))
                .addMappings(mapper -> mapper.when(isNotNull()).map(VehicleDto::getLoadCapacity, Vehicle::setLoadCapacity))

                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(VehicleDto::getCity, Vehicle::setCity))
                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(VehicleDto::getCarriage, Vehicle::setCarriage))
                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(VehicleDto::getCoDrivers, Vehicle::setCoDrivers));
    }

    /**
     * DriverDto to Driver mapping config
     *
     * @return type map driverDto -> driver
     */
    @Bean
    public TypeMap<DriverDto, Driver> getDriverDtoDriverTypeMap() {
        return getModelMapper().createTypeMap(DriverDto.class, Driver.class)
                .addMappings(mapper -> mapper.when(isNotNull()).map(DriverDto::getPersonalNum, Driver::setPersonalNum))
                .addMappings(mapper -> mapper.when(isNotNull()).map(DriverDto::getStatus, Driver::setStatus))

                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(DriverDto::getCity, Driver::setCity))
                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(DriverDto::getCarriage, Driver::setCarriage))
                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(DriverDto::getUser, Driver::setUser))
                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(DriverDto::getVehicle, Driver::setVehicle))
                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(DriverDto::getShifts, Driver::setShifts));
    }

    /**
     * CityDto to City mapping config
     *
     * @return type map CityDto -> City
     */
    @Bean
    public TypeMap<CityDto, City> getCityDtoCityTypeMap() {
        return getModelMapper().createTypeMap(CityDto.class, City.class, "CityDtoCity")
                .addMappings(mapper -> mapper.when(isNotNull()).map(CityDto::getName, City::setName))
                .addMappings(mapper -> mapper.when(isNotNull()).map(CityDto::getLatitude, City::setLatitude))
                .addMappings(mapper -> mapper.when(isNotNull()).map(CityDto::getLongitude, City::setLongitude))

                .addMappings(mapper -> mapper.skip(City::setWaypoints))
                .addMappings(mapper -> mapper.skip(City::setDrivers))
                .addMappings(mapper -> mapper.skip(City::setVehicles));
    }

    /**
     * Carriage to CarriageDto mapping config
     *
     * @return type map Carriage -> CarriageDto
     */
    @Bean
    public TypeMap<Carriage, CarriageDto> getCarriageCarriageDtoTypeMap() {
        Converter<List<Driver>, List<DriverDto>> toDriverDtoList = new AbstractConverter<List<Driver>, List<DriverDto>>() {
            protected List<DriverDto> convert(List<Driver> source) {
                if (source == null) {
                    return null;
                } else {
                    List<DriverDto> driverDtos = new ArrayList<>(source.size());
                    for (Driver driver : source) {
                        driverDtos.add(getModelMapper().getTypeMap(Driver.class, DriverDto.class, "DriverDriverDto").map(driver));
                    }
                    return driverDtos;
                }
            }
        };

        Converter<Vehicle, VehicleDto> toVehicleDto = new AbstractConverter<Vehicle, VehicleDto>() {
            @Override
            protected VehicleDto convert(Vehicle vehicle) {
                if (vehicle == null) {
                    return null;
                } else {
                    return getModelMapper().getTypeMap(Vehicle.class, VehicleDto.class, "VehicleVehicleDto")
                            .map(vehicle);
                }
            }
        };

        /*

        Converter<?  super Cargo, CargoDto> toCargoDto = new AbstractConverter<Cargo, CargoDto>() {
            @Override
            protected CargoDto convert(Cargo cargo) {
                if (cargo == null) {
                    return null;
                } else {
                    return getModelMapper().getTypeMap(Cargo.class, CargoDto.class, "CargoCargoDto")
                            .map(cargo);
                }
            }
        };*/

        return getModelMapper().createTypeMap(Carriage.class, CarriageDto.class, "CarriageCarriageDto")
                .addMappings(mapper -> mapper.skip(CarriageDto::setMaxWeight))

//                .addMappings(mapper -> mapper.using(toCargoDto).map(Carriage::getCargoes, CarriageDto::setCargoes))
//                .addMappings(mapper -> mapper.using(toWaypointDto).map(Carriage::getWaypoints, CarriageDto::setWaypoints))
                .addMappings(mapper -> mapper.using(toVehicleDto).map(Carriage::getVehicle, CarriageDto::setVehicle))
                .addMappings(mapper -> mapper.using(toDriverDtoList).map(Carriage::getDrivers, CarriageDto::setDrivers));
    }

    /**
     * WaypointDto to Waypoint mapping config
     *
     * @return type map WaypointDto -> Waypoint
     */
    @Bean
    public TypeMap<WaypointDto, Waypoint> getWaypointDtoWaypointTypeMap() {
        return getModelMapper().createTypeMap(WaypointDto.class, Waypoint.class, "WaypointDtoWaypoint")
                .addMappings(mapper -> mapper.when(isNotNull()).map(WaypointDto::getPosition, Waypoint::setPosition))

                .addMappings(mapper -> mapper.skip(Waypoint::setToThisWp))
                .addMappings(mapper -> mapper.skip(Waypoint::setFromThisWp))

                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(WaypointDto::getCity, Waypoint::setCity))
                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(WaypointDto::getCarriage, Waypoint::setCarriage));
    }


    @Bean
    public TypeMap<Cargo, CargoDto> getCargoCargoDtoTypeMap() {

        return getModelMapper().createTypeMap(Cargo.class, CargoDto.class)
                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(Cargo::getCarriage, CargoDto::setCarriage))

                .addMappings(mapper -> mapper.using(getToWaypointDtoConverter()).map(Cargo::getDepartureWaypoint, CargoDto::setDepartureWaypoint))
                .addMappings(mapper -> mapper.using(getToWaypointDtoConverter()).map(Cargo::getDestinationWaypoint, CargoDto::setDestinationWaypoint));
    }

    @Bean
    public TypeMap<Waypoint, WaypointDto> getWaypointWaypointDtoTypeMap() {
        return getModelMapper().createTypeMap(Waypoint.class, WaypointDto.class)
                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(Waypoint::getCarriage, WaypointDto::setCarriage));
    }

    @Bean
    public TypeMap<Driver, DriverDto> getDriverDriverDtoTypeMap() {
        return getModelMapper().createTypeMap(Driver.class, DriverDto.class, "DriverDriverDto")
                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(Driver::getCarriage, DriverDto::setCarriage))
                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(Driver::getShifts, DriverDto::setShifts))
                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(Driver::getVehicle, DriverDto::setVehicle));
    }


    @Bean
    public TypeMap<Vehicle, VehicleDto> getVehicleVehicleDtoTypeMap() {
        return getModelMapper().createTypeMap(Vehicle.class, VehicleDto.class, "VehicleVehicleDto")
                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(Vehicle::getCarriage, VehicleDto::setCarriage))
                .addMappings(mapper -> mapper.using(MappingContext::getDestination).map(Vehicle::getCoDrivers, VehicleDto::setCoDrivers));
    }

    @Bean
    public Converter<Waypoint, WaypointDto> getToWaypointDtoConverter() {
        return new AbstractConverter<Waypoint, WaypointDto>() {
            @Override
            protected WaypointDto convert(Waypoint waypoint) {
                if (waypoint == null) {
                    return null;
                } else {
                    return getModelMapper().getTypeMap(Waypoint.class, WaypointDto.class)
                            .map(waypoint);
                }
            }
        };
    }
}
