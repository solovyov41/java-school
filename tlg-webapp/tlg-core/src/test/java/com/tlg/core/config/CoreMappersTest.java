package com.tlg.core.config;

import com.tlg.core.dto.CityDto;
import com.tlg.core.dto.VehicleDto;
import com.tlg.core.entity.City;
import com.tlg.core.entity.Vehicle;
import com.tlg.core.entity.enums.VehicleState;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CoreMappers.class)
public class CoreMappersTest {

    @Autowired
    ModelMapper modelMapper;

    VehicleDto vehicleDto;

    @Before
    public void setUp() {
        vehicleDto = new VehicleDto();
        vehicleDto.setLicPlateNum("AA00001");
        vehicleDto.setPassSeatsNum(3);
        vehicleDto.setLoadCapacity(BigDecimal.valueOf(1.5));
        vehicleDto.setState(VehicleState.OK);
        CityDto cityDto = new CityDto();
        cityDto.setName("MyCity");
        vehicleDto.setCity(cityDto);
    }

    @Test
    public void newVehicleDtoVehicleTypeMap() {
        Vehicle vehicle = modelMapper.map(vehicleDto, Vehicle.class);

        assertEquals(vehicleDto.getLicPlateNum(), vehicle.getLicPlateNum());
        assertEquals(vehicleDto.getPassSeatsNum(), vehicle.getPassSeatsNum());
        assertEquals(vehicleDto.getLoadCapacity(), vehicle.getLoadCapacity());
        assertEquals(vehicleDto.getState(), vehicle.getState());

        assertNull(vehicle.getCity());
    }

    @Test
    public void updateVehicleDtoVehicleTypeMap() {
        Vehicle vehicle = new Vehicle();
        vehicle.setLicPlateNum("ZZ00001");
        vehicle.setPassSeatsNum(1);
        vehicle.setLoadCapacity(BigDecimal.valueOf(10));
        vehicle.setState(VehicleState.BROKEN);

        City city = new City();
        city.setName("OldCity");
        city.setLatitude(30.);
        city.setLongitude(50.);
        vehicle.setCity(city);

        modelMapper.map(vehicleDto, vehicle);

        assertEquals(vehicleDto.getLicPlateNum(), vehicle.getLicPlateNum());
        assertEquals(vehicleDto.getPassSeatsNum(), vehicle.getPassSeatsNum());
        assertEquals(vehicleDto.getLoadCapacity(), vehicle.getLoadCapacity());
        assertEquals(vehicleDto.getState(), vehicle.getState());

        assertNotEquals(vehicleDto.getCity().getName(), vehicle.getCity().getName());
        assertNotNull(vehicle.getCity().getLatitude());
        assertNotNull(vehicle.getCity().getLongitude());
    }

    @Test
    public void getCarriageDtoCarriageTypeMap() {

    }
}