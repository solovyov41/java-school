package com.tlg.data;

import com.tlg.model.Carriage;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Comparator;
import java.util.List;

@ApplicationScoped
public class CarriageListProducer {

    private List<Carriage> orders;

//    @Inject
//    private CarriageDao carriageDao;

    @Inject
    private Event<List<Carriage>> ordersEvent;

    @Produces
    @Named
    public List<Carriage> getOrders() {
        return orders;
    }

    public void setOrders(List<Carriage> orders) {
        orders.sort(Comparator.comparing(Carriage::getInitiateDate));
        this.orders = orders;
    }

    public void updateOrders(Carriage order) {
        int index = orders.indexOf(order);
        if (index != -1) {
            orders.set(index, order);
        } else {
            orders.add(order);
            if (orders.size()>10){
                orders.remove(0);
            }
        }
        ordersEvent.fire(orders);
    }

//    public void onOrderListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Carriage carriage) {
//        retrieveAllOrdersOrderedByCreationDate();
//    }
//
//    @PostConstruct
//    public void retrieveAllOrdersOrderedByCreationDate() {
//        orders = carriageDao.findAllOrderedByCreationDate();
//        ordersEvent.fire(orders);
//    }
}
