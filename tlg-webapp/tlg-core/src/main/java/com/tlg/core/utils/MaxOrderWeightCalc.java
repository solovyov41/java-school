package com.tlg.core.utils;

import com.tlg.core.dto.CarriageDto;
import com.tlg.core.entity.Carriage;

import java.math.BigDecimal;

public interface MaxOrderWeightCalc {

    BigDecimal calculate(Carriage carriage);

    BigDecimal calculate(CarriageDto carriage);
}
