package com.tlg.util;

import com.tlg.data.CarriageListProducer;
import com.tlg.data.DriverStatisticsProducer;
import com.tlg.data.VehicleStatisticsProducer;
import com.tlg.dto.CarriageDto;
import com.tlg.model.Carriage;
import com.tlg.model.DriverStatistics;
import com.tlg.model.VehicleStatistics;
import com.tlg.service.OrderService;
import org.jboss.ejb3.annotation.ResourceAdapter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;


@MessageDriven(mappedName = "ArtemisMdb", activationConfig = {
        @ActivationConfigProperty(propertyName = "useJNDI", propertyValue = "false"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "tlgTopic"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
@ResourceAdapter("remote-artemis")
public class ArtemisMdb implements MessageListener {

    @Inject
    private Logger logger;

    @Inject
    CarriageListProducer carriageListProducer;
//    OrderService orderService;

    @Inject
    DriverStatisticsProducer driverStatisticsProducer;

    @Inject
    VehicleStatisticsProducer vehicleStatisticsProducer;

    @Inject
    JsonbConfig jsonbConfig;

    @Inject
    ModelMapper modelMapper;

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {

            try (Jsonb jsonb = JsonbBuilder.create(jsonbConfig)) {
                String text = ((TextMessage) message).getText();
                ChangesType changesType = ChangesType.valueOf(message.getStringProperty(ChangesType.class.getSimpleName()));
                switch (changesType) {
                    case NEW_ORDER:
//                        CarriageDto newCarriage = jsonb.fromJson(text, CarriageDto.class);
//                        orderService.createOrder(newCarriage);
//                        break;
                    case UPDATE_ORDER:
                        CarriageDto carriage = jsonb.fromJson(text, CarriageDto.class);
                        carriageListProducer.updateOrders(modelMapper.map(carriage, Carriage.class));
//                        orderService.updateOrder(carriage);
                        break;
                    case DRIVER_STATISTICS:
                        DriverStatistics driverStatistics = jsonb.fromJson(text, DriverStatistics.class);
                        driverStatisticsProducer.updateDriverStat(driverStatistics);
                        break;
                    case VEHICLE_STATISTICS:
                        VehicleStatistics vehicleStatistics = jsonb.fromJson(text, VehicleStatistics.class);
                        vehicleStatisticsProducer.updateVehicleStat(vehicleStatistics);
                        break;
                }
            } catch (JMSException ex) {
                logger.warn(ex.getErrorCode());
            } catch (Exception e) {
                logger.warn("Unable get message");
            }
        }

    }

    private enum ChangesType {
        DRIVER_STATISTICS, VEHICLE_STATISTICS, NEW_ORDER, UPDATE_ORDER
    }
}


