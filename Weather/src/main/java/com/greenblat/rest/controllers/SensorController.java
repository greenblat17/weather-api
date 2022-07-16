package com.greenblat.rest.controllers;

import com.greenblat.rest.dto.SensorDTO;
import com.greenblat.rest.models.Sensor;
import com.greenblat.rest.services.SensorsService;
import com.greenblat.rest.util.SensorValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/sensor")
public class SensorController {

    private final SensorValidator sensorValidator;
    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorValidator sensorValidator, SensorsService sensorsService, ModelMapper modelMapper) {
        this.sensorValidator = sensorValidator;
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) {
        sensorValidator.validate(modelMapper.map(sensorDTO, Sensor.class), bindingResult);

        sensorsService.save(modelMapper.map(sensorDTO, Sensor.class));

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
