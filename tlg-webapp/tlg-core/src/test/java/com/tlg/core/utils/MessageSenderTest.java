package com.tlg.core.utils;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.tlg.core.config.CoreConfig;
import com.tlg.core.dto.CarriageDto;
import com.tlg.core.dto.DriverDto;
import com.tlg.core.dto.VehicleDto;
import com.tlg.core.entity.User;
import com.tlg.core.entity.enums.CarriageStatus;
import com.tlg.core.entity.enums.DriverStatus;
import com.tlg.core.entity.enums.VehicleState;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CoreConfig.class})
public class MessageSenderTest {
    @Autowired
    MessageSender messageSender;

    @Ignore
    @Test
    public void sendNewOrderMessage() {
        CarriageDto carriage = new CarriageDto();
        carriage.setUniqueNumber("ODR-00000001");
        carriage.setInitiateDate(new Date());
        carriage.setCustomerName("The best customer");
        carriage.setStatus(CarriageStatus.CREATED);
        carriage.setEstimatedLeadTime(8);

        VehicleDto vehicle = new VehicleDto();
        vehicle.setLicPlateNum("AA00001");
        vehicle.setState(VehicleState.OK);
        vehicle.setPassSeatsNum(2);
        carriage.setVehicle(vehicle);

        DriverDto driver1 = new DriverDto();
        driver1.setPersonalNum("DVR-00000001");
        driver1.setStatus(DriverStatus.DRIVE);
        User user = new User();
        user.setEmail("email1@tlg.com");
        user.setName("name1");
        user.setSurname("surname1");
        driver1.setUser(user);
        DriverDto driver2 = new DriverDto();
        driver1.setPersonalNum("DVR-00000002");
        driver1.setStatus(DriverStatus.IN_SHIFT);
        User user2 = new User();
        user.setEmail("email2@tlg.com");
        user.setName("name2");
        user.setSurname("surname2");
        driver1.setUser(user2);
        carriage.setDrivers(Arrays.asList(driver1, driver2));

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            objectMapper.setDateFormat(new StdDateFormat());

            String carriageAsString = objectMapper.writeValueAsString(carriage);
            messageSender.sendMessage(ChangesType.NEW_ORDER, carriageAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        assertTrue(true);
    }

    @Ignore
    @Test
    public void sendDriverStatistic() {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            JsonFactory jfactory = new JsonFactory();
            JsonGenerator jGenerator = jfactory.createGenerator(stream, JsonEncoding.UTF8);

            jGenerator.writeStartObject();
            jGenerator.writeNumberField("total", 2);
            jGenerator.writeNumberField("free", 1);
            jGenerator.writeNumberField("inWork", 1);
            jGenerator.writeEndObject();
            jGenerator.close();

            String driverStat = new String(stream.toByteArray(), StandardCharsets.UTF_8);
            messageSender.sendMessage(ChangesType.DRIVER_STATISTICS, driverStat);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}