package app.ecosynergy.api.controllers;

import app.ecosynergy.api.data.vo.v1.MQ7ReadingVO;
import app.ecosynergy.api.services.MQ7ReadingServices;
import app.ecosynergy.api.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@Tag(name = "MQ7 Sensor Readings Endpoint")
@RestController
@RequestMapping("/api/mq7Reading/v1")
public class MQ7ReadingController {
    @Autowired
    MQ7ReadingServices service;

    @Operation(summary = "Find MQ7 reading by ID", description = "Retrieve an MQ7 sensor reading by ID")
    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public MQ7ReadingVO findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @Operation(summary = "Get all MQ7 readings", description = "Retrieve a list of all MQ7 sensor readings")
    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public List<MQ7ReadingVO> findAll(){
        return service.findAll();
    }

    @Operation(summary = "Create a new MQ7 reading", description = "Create a new MQ7 sensor reading with the provided data")
    @PostMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public MQ7ReadingVO create(@RequestBody MQ7ReadingVO reading){
        reading.setDate(ZonedDateTime.now());

        return service.create(reading);
    }
}