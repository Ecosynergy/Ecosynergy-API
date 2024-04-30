package app.ecosynergy.api.controllers;

import app.ecosynergy.api.data.vo.v1.FireReadingVO;
import app.ecosynergy.api.services.FireReadingServices;
import app.ecosynergy.api.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@Tag(name = "Fire Readings Endpoint")
@RestController
@RequestMapping("/api/fireReading/v1")
public class FireReadingController {
    @Autowired
    FireReadingServices service;

    @Operation(summary = "Find fire reading by ID", description = "Retrieve a fire reading by ID")
    @GetMapping(value= "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public FireReadingVO findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @Operation(summary = "Get all fire readings", description = "Retrieve a list of all fire readings")
    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public List<FireReadingVO> findAll(){
        return service.findAll();
    }

    @Operation(summary = "Create a new fire reading", description = "Create a new fire reading with the current data")
    @PostMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public FireReadingVO create(@RequestBody FireReadingVO reading){
        reading.setDate(ZonedDateTime.now());

        return service.create(reading);
    }
}
