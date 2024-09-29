package app.ecosynergy.api.controllers;

import app.ecosynergy.api.data.vo.v1.FireReadingVO;
import app.ecosynergy.api.services.FireReadingService;
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
    FireReadingService service;

    @Operation(summary = "Find fire reading by ID", description = "Retrieve a fire reading by ID")
    @GetMapping(value= "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public FireReadingVO findById (@PathVariable("id") Long id
    ){
        return service.findById(id);
    }

    @Operation(summary = "Get all fire readings", description = "Retrieve a list of all fire readings")
    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    public ResponseEntity<PagedModel<EntityModel<FireReadingVO>>> findAll (
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        page--;

        if(limit == null) limit = (int) service.countAllReadings();

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "timestamp"));

        return ResponseEntity.ok(service.findAll(pageable));
    }

    @Operation(summary = "Get fire readings by team handle", description = "Retrieve a list of fire readings by team handle")
    @GetMapping(
            value = "/team/{teamHandle}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public ResponseEntity<PagedModel<EntityModel<FireReadingVO>>> findByTeamHandle (
            @PathVariable("teamHandle") String teamHandle,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ){
        page--;

        if(limit == null) limit = (int) service.countAllReadings();

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "timestamp"));

        return ResponseEntity.ok(service.findByTeamHandle(teamHandle, pageable));
    }

    @Operation(summary = "Create a new fire reading", description = "Create a new fire reading with the current data")
    @PostMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public FireReadingVO create (@RequestBody FireReadingVO reading
    ){
        reading.setTimestamp(ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).truncatedTo(ChronoUnit.SECONDS));

        return service.create(reading);
    }
}
