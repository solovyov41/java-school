package com.tlg.web.controller;

import com.tlg.core.dto.CargoDto;
import com.tlg.core.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager/cargo")
public class CargoController {

    @Autowired
    CargoService cargoService;

    @GetMapping("/new")
    public CargoDto getNewUniqueNumber() {
        return cargoService.getNewCargo();
    }
}

