package com.tlg.core.dao;

import com.tlg.core.dao.exception.DaoException;
import com.tlg.core.entity.Cargo;
import com.tlg.core.entity.Carriage;

import java.util.List;

public interface CargoDao extends GenericDao<Cargo, Long> {
    List<Cargo> findByOrder(Carriage carriage) throws DaoException;

    Cargo findByUniqueNumber(String uniqueNumber) throws DaoException;
}
