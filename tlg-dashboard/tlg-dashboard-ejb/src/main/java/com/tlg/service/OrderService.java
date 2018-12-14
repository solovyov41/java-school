package com.tlg.service;

import com.tlg.data.CarriageDao;
import com.tlg.model.Carriage;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class OrderService {
    @Inject
    private Logger log;
    @Inject
    private CarriageDao carriageDao;
    @Inject
    private Event<Carriage> carriageEvent;
    @Inject
    private ModelMapper modelMapper;

    public void createOrders(List<Carriage> carriages) {

        for (Carriage carriage : carriages) {
            log.info("Recived {}", carriage);
            carriageDao.create(carriage);
        }
    }

    public void createOrder(Carriage carriage) {
        log.info("New order {}", carriage);
        carriageDao.create(carriage);
        carriageEvent.fire(carriage);
    }

    public void updateOrder(Carriage carriage) {

        Carriage curCarriage = carriageDao.findByUniqueNymber(carriage.getUniqueNumber());
        log.info("Update order {}", curCarriage);
        modelMapper.map(carriage, curCarriage);
        carriageDao.update(curCarriage);
        carriageEvent.fire(curCarriage);
    }
}
