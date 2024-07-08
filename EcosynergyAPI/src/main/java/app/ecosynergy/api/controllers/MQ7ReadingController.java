package app.ecosynergy.api.controllers;

import app.ecosynergy.api.data.vo.v1.MQ7ReadingVO;
import app.ecosynergy.api.services.MQ7ReadingServices;
import app.ecosynergy.api.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Tag(name = "MQ7 Sensor Readings Endpoint")
@RestController
@RequestMapping("/api/mq7Reading/v1")
public class MQ7ReadingController {
    @Autowired
    MQ7ReadingServices service;

    @Operation(summary = "Find MQ7 reading by ID", description = "Retrieve an MQ7 sensor reading by ID")
    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public MQ7ReadingVO findById(@PathVariable("id") Long id,
                                 @RequestHeader(value = "Time-Zone", required = false) String timeZone
    ){
        ZoneId zoneId = timeZone != null ? ZoneId.of(timeZone) : ZoneId.of("UTC");

        return service.findById(id, zoneId);
    }

    @Operation(summary = "Get all MQ7 readings", description = "Retrieve a list of all MQ7 sensor readings")
    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    public ResponseEntity<PagedModel<EntityModel<MQ7ReadingVO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            @RequestHeader(value = "Time-Zone", required = false) String timeZone
    ){
        page--;

        if(limit == null)  limit = (int) service.countAllReadings();

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "date"));

        ZoneId zoneId = timeZone != null ? ZoneId.of(timeZone) : ZoneId.of("UTC");
        return ResponseEntity.ok(service.findAll(pageable,zoneId));
    }

    @Operation(summary = "Create a new MQ7 reading", description = "Create a new MQ7 sensor reading with the provided data")
    @PostMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public MQ7ReadingVO create(@RequestBody MQ7ReadingVO reading,
                               @RequestHeader(value = "Time-Zone", required = false) String timeZone
    ){
        reading.setDate(ZonedDateTime.now());

        ZoneId zoneId = timeZone != null ? ZoneId.of(timeZone) : ZoneId.of("UTC");

        return service.create(reading, zoneId);
    }
}