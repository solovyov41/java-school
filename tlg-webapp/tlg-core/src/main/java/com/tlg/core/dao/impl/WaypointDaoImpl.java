package com.tlg.core.dao.impl;

import com.tlg.core.dao.WaypointDao;
import com.tlg.core.entity.Waypoint;
import org.springframework.stereotype.Repository;

@Repository("waypointDao")
public class WaypointDaoImpl extends AbstractDao<Waypoint, Long> implements WaypointDao {

    public WaypointDaoImpl() {
        super(Waypoint.class);
    }

}
