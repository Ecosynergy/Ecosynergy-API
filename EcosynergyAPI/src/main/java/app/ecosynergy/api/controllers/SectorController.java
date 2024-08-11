package app.ecosynergy.api.controllers;

import app.ecosynergy.api.data.vo.v1.SectorVO;
import app.ecosynergy.api.data.vo.v1.views.Views;
import app.ecosynergy.api.services.SectorServices;
import app.ecosynergy.api.util.MediaType;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sector/v1")
public class SectorController {
    @Autowired
    SectorServices services;

    @GetMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @JsonView(Views.SectorView.class)
    public ResponseEntity<List<SectorVO>> findAll() {
        List<SectorVO> sectorVOs = services.findAll();

        return ResponseEntity.ok(sectorVOs);
    }

    @GetMapping(
            value = "/id/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @JsonView(Views.SectorView.class)
    public ResponseEntity<SectorVO> findById(@PathVariable("id") Long id) {
        SectorVO sectorVO = services.findById(id);

        return ResponseEntity.ok(sectorVO);
    }

    @GetMapping(
            value = "/activity/{activityId}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @JsonView(Views.SectorView.class)
    public ResponseEntity<SectorVO> findByActivity(@PathVariable("activityId") Long activityId) {
        SectorVO sectorVO = services.findByActivity(activityId);

        return ResponseEntity.ok(sectorVO);
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @JsonView(Views.SectorView.class)
    public ResponseEntity<SectorVO> create(@RequestBody SectorVO sector) {
        SectorVO sectorVO = services.create(sector);

        return ResponseEntity.ok(sectorVO);
    }

    @PutMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @JsonView(Views.SectorView.class)
    public ResponseEntity<SectorVO> update(@PathVariable("id") Long id,
                                           @RequestBody SectorVO sectorVO
    ) {
        SectorVO updatedSectorVO = services.update(id, sectorVO);

        return ResponseEntity.ok(updatedSectorVO);
    }

    @DeleteMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    @JsonView(Views.SectorView.class)
    public ResponseEntity<?> delete(@PathVariable("id") Long id
    ) {
        services.delete(id);

        return ResponseEntity.noContent().build();
    }
}
