package com.tlg.util;

import com.tlg.data.CarriageListProducer;
import com.tlg.data.DriverStatisticsProducer;
import com.tlg.data.VehicleStatisticsProducer;
import com.tlg.dto.CarriageDto;
import com.tlg.model.Carriage;
import com.tlg.model.DriverStatistics;
import com.tlg.model.VehicleStatistics;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Startup
public class InitialDataLoader {

    private static final String USER_AGENT = "TLG-Dashboard";
    @Inject
    Logger logger;

    @Inject
    DriverStatisticsProducer driverStatisticsProducer;

    @Inject
    VehicleStatisticsProducer vehicleStatisticsProducer;

    @Inject
    CarriageListProducer carriageListProducer;
//    CarriageDao carriageDao;

    @Inject
    JsonbConfig jsonbConfig;
    @Inject
    ModelMapper modelMapper;

    @PostConstruct
    private void loadData() {
        String url = "http://localhost:8080/tlg-webapp/rest";

        getDriverStatistics(url + "/driver/stat");
        getVehicleStatistics(url + "/vehicle/stat");
        getLastOrders(url + "/carriage");
    }

    private void getDriverStatistics(String url) {
        try (Jsonb jsonb = JsonbBuilder.create()) {
            HttpResponse response = getHttpResponse(url);
            DriverStatistics driverStatistics = jsonb.fromJson(response.getEntity().getContent(), DriverStatistics.class);
            driverStatisticsProducer.setDriverStat(driverStatistics);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    private void getVehicleStatistics(String url) {
        try (Jsonb jsonb = JsonbBuilder.create()) {
            HttpResponse response = getHttpResponse(url);
            VehicleStatistics vehicleStatistics = jsonb.fromJson(response.getEntity().getContent(), VehicleStatistics.class);
            vehicleStatisticsProducer.setVehicleStat(vehicleStatistics);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    private void getLastOrders(String url) {
        try (Jsonb jsonb = JsonbBuilder.create(jsonbConfig)) {
            HttpResponse response = getHttpResponse(url);

            List<CarriageDto> carriageDtos = jsonb.fromJson(response.getEntity().getContent(), new ArrayList<CarriageDto>() {
            }.getClass().getGenericSuperclass());

            List<Carriage> carriages = new ArrayList<>(carriageDtos.size());
            for (CarriageDto carriageDto : carriageDtos) {
                carriages.add(modelMapper.map(carriageDto, Carriage.class));
            }

//            carriageDao.create(carriages);
            carriageListProducer.setOrders(carriages);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
    }

    private HttpResponse getHttpResponse(String url) throws HttpException {
        HttpGet request = new HttpGet(url);
        request.addHeader("User-Agent", USER_AGENT);
        request.addHeader("accept", "application/json");

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() != 200) {
                logger.warn("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
                throw new HttpException("Failed request to " + url + ": HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }
            return response;

        } catch (IOException e) {
            logger.warn(e.getMessage());
            throw new HttpException("Failed to execute http request", e);
        }
    }

}