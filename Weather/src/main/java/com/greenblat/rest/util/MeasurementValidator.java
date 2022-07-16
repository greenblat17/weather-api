package com.greenblat.rest.util;

import com.greenblat.rest.models.Measurement;
import com.greenblat.rest.models.Sensor;
import com.greenblat.rest.services.SensorsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class MeasurementValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public MeasurementValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Validator.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;

        Optional<Sensor> searchSensor = sensorsService.findByName(measurement.getSensor().getName());
        if (!searchSensor.isPresent()) {
            errors.rejectValue("sensor", "", "Sensor with the same name doesn't exist");
        }
    }
}
