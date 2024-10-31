package app.ecosynergy.api.controllers;

import app.ecosynergy.api.data.vo.v1.MQ135ReadingVO;
import app.ecosynergy.api.services.MQ135ReadingService;
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

@Tag(name = "MQ135 Sensor Readings Endpoint")
@RestController
@RequestMapping("/api/mq135Reading/v1")
public class MQ135ReadingController {
    @Autowired
    MQ135ReadingService service;

    @Operation(summary = "Find MQ135 reading by ID", description = "Retrieve an MQ135 sensor reading by ID")
    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public MQ135ReadingVO findById(@PathVariable("id") Long id
    ) {

        return service.findById(id);
    }

    @Operation(summary = "Get all MQ135 readings", description = "Retrieve a list of all MQ135 sensor readings")
    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public ResponseEntity<PagedModel<EntityModel<MQ135ReadingVO>>> findAll(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        page--;

        if (limit == null) limit = (int) service.countAllReadings();

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "timestamp"));

        return ResponseEntity.ok(service.findAll(pageable));
    }

    @Operation(summary = "Get MQ135 readings by team handle", description = "Retrieve a list of MQ135 readings by team handle")
    @GetMapping(
            value = "/team/{teamHandle}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public ResponseEntity<PagedModel<EntityModel<MQ135ReadingVO>>> findByTeamHandle(
            @PathVariable("teamHandle") String teamHandle,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", required = false) Integer limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        page--;

        if (limit == null) limit = (int) service.countAllReadings();

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "timestamp"));

        return ResponseEntity.ok(service.findByTeamHandle(teamHandle, pageable));
    }

    @Operation(summary = "Create a new MQ135 reading", description = "Create a new MQ135 sensor reading with the provided data")
    @PostMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public MQ135ReadingVO create(@RequestBody MQ135ReadingVO reading
    ) {
        reading.setTimestamp(ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC")).truncatedTo(ChronoUnit.SECONDS));

        return service.create(reading);
    }
}
