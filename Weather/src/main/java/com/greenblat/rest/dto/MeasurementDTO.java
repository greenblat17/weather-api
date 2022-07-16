package com.greenblat.rest.dto;


import javax.validation.constraints.*;

public class MeasurementDTO {

    @NotNull(message = "Value should not be empty")
    @Min(value = -100, message = "Value should be greater than -100")
    @Max(value = 100, message = "Value should be less than 100")
    private Double value;

    @NotNull(message = "Raining should not be empty")
    private Boolean raining;


    @NotNull(message = "Sensor should be exist")
    private SensorDTO sensorDTO;

    public Double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensorDTO;
    }

    public void setSensor(SensorDTO sensorDTO) {
        this.sensorDTO = sensorDTO;
    }
}
