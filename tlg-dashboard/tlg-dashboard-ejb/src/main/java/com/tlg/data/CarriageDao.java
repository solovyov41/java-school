package com.tlg.data;

import com.tlg.model.Carriage;
import com.tlg.model.Waypoint;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@ApplicationScoped
public class CarriageDao {

    @Inject
    private EntityManager entityManager;

    public Carriage findById(Long id) {
        return entityManager.find(Carriage.class, id);
    }

    public Carriage findByUniqueNymber(String uniqueNumber) {
        TypedQuery<Carriage> query = entityManager.createNamedQuery("Carriage.findByUniqueNumber", Carriage.class);
        query.setParameter("uniqueNumber", uniqueNumber);

        return query.getSingleResult();
    }

    public List<Carriage> findAllOrderedByCreationDate() {
        TypedQuery<Carriage> query = entityManager.createNamedQuery("Carriage.findAllOrderedByDate", Carriage.class);
        return query.getResultList();
    }

    public Carriage update(Carriage carriage){
        return entityManager.merge(carriage);
    }

    public void create(Carriage carriage){
        carriage.getWaypoints().forEach(waypoint -> waypoint.setCarriage(carriage));
        carriage.getCargoes().forEach(cargo -> cargo.setCarriage(carriage));
        carriage.getDrivers().forEach(driver -> driver.setCarriage(carriage));
        carriage.getVehicle().setCarriage(carriage);

         entityManager.persist(carriage);
    }

    public  void create(List<Carriage> carriages) {
        for (Carriage carriage : carriages){
            create(carriage);
        }
    }
}
