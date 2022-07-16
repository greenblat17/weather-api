package com.greenblat.rest.services;

import com.greenblat.rest.models.Measurement;
import com.greenblat.rest.models.Sensor;
import com.greenblat.rest.repositories.MeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {

    private final MeasurementsRepository measurementsRepository;
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsService sensorsService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsService = sensorsService;
    }

    public List<Measurement> findAll() {
        return measurementsRepository.findAll();
    }

    @Transactional
    public void save(Measurement measurement) {
        measurement.setCreatedAt(LocalDateTime.now());

        Optional<Sensor> sensor = sensorsService.findByName(measurement.getSensor().getName());
        sensor.ifPresent(measurement::setSensor);

        measurementsRepository.save(measurement);
    }

    public long findByRainyDay() {
        List<Measurement> measurements = measurementsRepository.findAll();
        return measurements.stream().filter(Measurement::isRaining).count();
    }

}
