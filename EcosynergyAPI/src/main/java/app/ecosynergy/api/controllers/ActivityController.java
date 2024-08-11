package app.ecosynergy.api.controllers;

import app.ecosynergy.api.data.vo.v1.ActivityVO;
import app.ecosynergy.api.services.ActivityServices;
import app.ecosynergy.api.util.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activity/v1")
public class ActivityController {
    @Autowired
    private ActivityServices services;

    @GetMapping(value = "/id/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public ResponseEntity<ActivityVO> findById(@PathVariable("id") Long id) {
        ActivityVO activityVO = services.findById(id);

        return ResponseEntity.ok(activityVO);
    }

    @GetMapping(value = "/name/{name}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public ResponseEntity<ActivityVO> findByName(@PathVariable("name") String name) {
        ActivityVO activityVO = services.findByName(name);

        return ResponseEntity.ok(activityVO);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    public ResponseEntity<List<ActivityVO>> findAll() {
        List<ActivityVO> activityVOS = services.findAll();

        return ResponseEntity.ok(activityVOS);
    }

    @GetMapping(value = "/sector/{sectorId}")
    public ResponseEntity<List<ActivityVO>> findBySectorId(@PathVariable("sectorId") Long sectorId) {
        List<ActivityVO> activityVOS = services.findBySectorId(sectorId);

        return ResponseEntity.ok(activityVOS);
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public ResponseEntity<ActivityVO> create(@RequestBody ActivityVO activityVO) {
        ActivityVO createdActivityVO = services.create(activityVO);

        return ResponseEntity.ok(createdActivityVO);
    }

    @PutMapping(
            value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public ResponseEntity<ActivityVO> update(
            @PathVariable("id") Long id,
            @RequestBody ActivityVO activityVO
    ) {
        ActivityVO updatedActivityVO = services.update(id, activityVO);

        return ResponseEntity.ok(updatedActivityVO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        services.delete(id);

        return ResponseEntity.noContent().build();
    }
}
