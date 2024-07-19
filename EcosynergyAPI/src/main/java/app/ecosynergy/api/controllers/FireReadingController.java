package app.ecosynergy.api.controllers;

import app.ecosynergy.api.data.vo.v1.FireReadingVO;
import app.ecosynergy.api.services.FireReadingServices;
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
import java.time.temporal.ChronoUnit;

@Tag(name = "Fire Readings Endpoint")
@RestController
@RequestMapping("/api/fireReading/v1")
public class FireReadingController {
    @Autowired
    FireReadingServices service;

    @Operation(summary = "Find fire reading by ID", description = "Retrieve a fire reading by ID")
    @GetMapping(value= "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public FireReadingVO findById(@PathVariable("id") Long id,
                                  @RequestHeader(value = "Time-Zone", required = false) String timeZone
    ){
        ZoneId zoneId = timeZone != null ? ZoneId.of(timeZone) :ZoneId.of("UTC");

        return service.findById(id, zoneId);
    }

    @Operation(summary = "Get all fire readings", description = "Retrieve a list of all fire readings")
    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    public ResponseEntity<PagedModel<EntityModel<FireReadingVO>>> findAll(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            @RequestHeader(value = "Time-Zone", required = false) String timeZone
    ){
        page--;

        if(limit == null) limit = (int) service.countAllReadings();

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "timestamp"));

        ZoneId zoneId = timeZone != null ? ZoneId.of(timeZone) :ZoneId.of("UTC");

        return ResponseEntity.ok(service.findAll(pageable, zoneId));
    }

    @Operation(summary = "Create a new fire reading", description = "Create a new fire reading with the current data")
    @PostMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public FireReadingVO create(@RequestBody FireReadingVO reading,
                                @RequestHeader(value = "Time-Zone", required = false) String timeZone
    ){
        reading.setTimestamp(ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).truncatedTo(ChronoUnit.SECONDS));

        ZoneId zoneId = timeZone != null ? ZoneId.of(timeZone) :ZoneId.of("UTC");

        return service.create(reading, zoneId);
    }
}
