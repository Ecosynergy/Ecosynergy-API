package app.ecosynergy.api.controllers;

import app.ecosynergy.api.util.MediaType;
import app.ecosynergy.api.data.vo.v1.TeamVO;
import app.ecosynergy.api.models.TeamMemberId;
import app.ecosynergy.api.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/api/team/v1")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    public ResponseEntity<PagedModel<EntityModel<TeamVO>>> findAll(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "5") Integer limit,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            @RequestHeader(value = "Time-Zone", defaultValue = "UTC", required = false) String timeZone
    ) {
        page--;

        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "createdAt"));

        ZoneId zoneId = ZoneId.of(timeZone);

        PagedModel<EntityModel<TeamVO>> teams = teamService.findAll(pageable, zoneId);

        return ResponseEntity.ok(teams);
    }

    @GetMapping(value = "/findId/{id}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    public ResponseEntity<TeamVO> findById(
            @PathVariable Long id,
            @RequestHeader(value = "Time-Zone", defaultValue = "UTC",required = false) String timeZone
    ) {
        ZoneId zoneId = ZoneId.of(timeZone);

        TeamVO team = teamService.findById(id, zoneId);

        return ResponseEntity.ok(team);
    }

    @GetMapping(value = "/findHandle/{handle}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    public ResponseEntity<TeamVO> findByHandle(
            @PathVariable String handle,
            @RequestHeader(value = "Time-Zone", defaultValue = "UTC",required = false) String timeZone
    ) {
        ZoneId zoneId = ZoneId.of(timeZone);

        TeamVO team = teamService.findByHandle(handle, zoneId);

        return ResponseEntity.ok(team);
    }

    @GetMapping(value = "/search/{handle}", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    public ResponseEntity<List<TeamVO>> searchTeamsByHandle(
            @PathVariable String handle,
            @RequestHeader(value = "Time-Zone", defaultValue = "UTC",required = false) String timeZone
    ) {
        ZoneId zoneId = ZoneId.of(timeZone);

        List<TeamVO> teams = teamService.findByHandleContaining(handle, zoneId);

        return ResponseEntity.ok(teams);
    }

    @PostMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public TeamVO create(
            @RequestBody TeamVO teamVO,
            @RequestHeader(value = "Time-Zone", defaultValue = "UTC",required = false) String timeZone
    ) {
        ZoneId zoneId = ZoneId.of(timeZone);

        return teamService.create(teamVO, zoneId);
    }

    @PutMapping(
            value = "/{teamId}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public TeamVO update(
            @PathVariable Long teamId,
            @RequestBody TeamVO teamVO,
            @RequestHeader(value = "Time-Zone", defaultValue = "UTC",required = false) String timeZone
    ) {
        ZoneId zoneId = ZoneId.of(timeZone);

        return teamService.update(teamId, teamVO, zoneId);
    }

    @DeleteMapping(value = "/{teamId}")
    public ResponseEntity<?> delete(@PathVariable Long teamId) {
        teamService.delete(teamId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(
            value = "/{teamId}/user/{userId}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public ResponseEntity<TeamVO> addMember(
            @PathVariable("teamId") Long teamId,
            @PathVariable("userId") Long userId,
            @RequestHeader(value = "Time-Zone", defaultValue = "UTC", required = false) String timeZone
    ){
        ZoneId zoneId = ZoneId.of(timeZone);

        TeamMemberId teamMemberId = new TeamMemberId(teamId, userId);

        TeamVO team = teamService.addMember(teamMemberId, zoneId);

        return ResponseEntity.ok(team);
    }

    @DeleteMapping(value = "/{teamId}/user/{userId}")
    public ResponseEntity<?> removeMember(
            @PathVariable("teamId") Long teamId,
            @PathVariable("userId") Long userId
    ){
        TeamMemberId teamMemberId = new TeamMemberId(teamId, userId);

        teamService.removeMember(teamMemberId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping(
            value = "/user/{userId}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public ResponseEntity<List<TeamVO>> findTeamsByUserId(
            @PathVariable Long userId,
            @RequestHeader(value = "Time-Zone", defaultValue = "UTC", required = false) String timeZone
    ) {
        ZoneId zoneId = ZoneId.of(timeZone);

        List<TeamVO> teamVOs = teamService.findTeamsByUserId(userId, zoneId);

        return ResponseEntity.ok(teamVOs);
    }
}