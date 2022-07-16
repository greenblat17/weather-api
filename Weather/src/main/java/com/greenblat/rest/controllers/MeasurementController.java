package com.greenblat.rest.controllers;

import com.greenblat.rest.dto.MeasurementDTO;
import com.greenblat.rest.dto.MeasurementResponse;
import com.greenblat.rest.models.Measurement;
import com.greenblat.rest.services.MeasurementsService;
import com.greenblat.rest.util.MeasurementErrorResponse;
import com.greenblat.rest.util.MeasurementNotCreatedException;
import com.greenblat.rest.util.MeasurementValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.greenblat.rest.util.FieldError.incorrectFields;

@RestController
@RequestMapping("/api/v1/measurements")
public class MeasurementController {

    private final MeasurementValidator measurementValidator;
    private final MeasurementsService measurementsServices;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementValidator measurementValidator, MeasurementsService measurementsServices, ModelMapper modelMapper) {
        this.measurementValidator = measurementValidator;
        this.measurementsServices = measurementsServices;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public MeasurementResponse getMeasurements() {
        List<MeasurementDTO> measurementDTOList = measurementsServices.findAll()
                .stream()
                .map(measurement -> modelMapper.map(measurement, MeasurementDTO.class))
                .collect(Collectors.toList());

        return new MeasurementResponse(measurementDTOList);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult) {
        measurementValidator.validate(modelMapper.map(measurementDTO, Measurement.class), bindingResult);
        if (bindingResult.hasErrors()) {
            incorrectFields(bindingResult);
        }

        measurementsServices.save(modelMapper.map(measurementDTO, Measurement.class));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount() {
        return measurementsServices.findByRainyDay();
    }

    @ExceptionHandler
    public ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException e) {
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
