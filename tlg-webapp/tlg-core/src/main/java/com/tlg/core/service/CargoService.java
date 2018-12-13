package com.tlg.core.service;

import com.tlg.core.dto.CargoDto;
import com.tlg.core.entity.Cargo;
import com.tlg.core.entity.Carriage;
import com.tlg.core.service.exceptions.ServiceException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CargoService extends GenericService<CargoDto, String> {

    CargoDto getNewCargo();

    @Transactional(propagation = Propagation.MANDATORY)
    List<Cargo> assignCargoes(List<CargoDto> cargoDtos, Carriage carriage) throws ServiceException;
}
