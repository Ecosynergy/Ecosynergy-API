package app.ecosynergy.api.services;

import app.ecosynergy.api.controllers.FireReadingController;
import app.ecosynergy.api.data.vo.v1.FireReadingVO;
import app.ecosynergy.api.exceptions.RequiredObjectIsNullException;
import app.ecosynergy.api.exceptions.ResourceNotFoundException;
import app.ecosynergy.api.mapper.DozerMapper;
import app.ecosynergy.api.models.FireReading;
import app.ecosynergy.api.models.Team;
import app.ecosynergy.api.repositories.FireReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.time.ZoneId;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class FireReadingServices {
    @Autowired
    private FireReadingRepository repository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private PagedResourcesAssembler<FireReadingVO> assembler;

    public FireReadingVO findById(Long id, ZoneId zoneId){
        if(id == null) throw new RequiredObjectIsNullException();

        FireReading reading = repository.findByIdWithTeam(id).orElseThrow(() -> new ResourceNotFoundException("Fire Reading not found with given ID: " + id));

        FireReadingVO vo = DozerMapper.parseObject(reading, FireReadingVO.class);
        vo.setTeamHandle(reading.getTeam().getHandle());
        vo.setTimestamp(vo.getTimestamp().withZoneSameInstant(zoneId));
        vo.add(linkTo(methodOn(FireReadingController.class).findById(vo.getKey(), zoneId.toString())).withSelfRel());
        return vo;
    }

    public PagedModel<EntityModel<FireReadingVO>> findAll(Pageable pageable, ZoneId zoneId){
        Page<FireReading> readingsPage = repository.findAllWithTeam(pageable);

        Page<FireReadingVO> voPage = readingsPage.map(r -> {
            FireReadingVO vo = DozerMapper.parseObject(r, FireReadingVO.class);
            vo.setTeamHandle(r.getTeam().getHandle());
            vo.setTimestamp(vo.getTimestamp().withZoneSameInstant(zoneId));
            return vo;
        });

        voPage.map(vo -> {
            try{
                return vo.add(linkTo(methodOn(FireReadingController.class).findById(vo.getKey(), zoneId.toString())).withSelfRel());
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        });

        Link link = linkTo(methodOn(FireReadingController.class)
                .findAll(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSort().toString(),
                        zoneId.toString()
                ))
                .withSelfRel();

        return assembler.toModel(voPage, link);
    }

    public FireReadingVO create(FireReadingVO reading, ZoneId zoneId){
        if(reading == null) throw new RequiredObjectIsNullException();

        Team team = DozerMapper.parseObject(
                teamService.findByHandle(reading.getTeamHandle(), zoneId),
                Team.class
        );

        FireReading readingEntity = DozerMapper.parseObject(reading, FireReading.class);
        readingEntity.setTeam(team);
        readingEntity = repository.save(readingEntity);

        FireReadingVO vo = DozerMapper.parseObject(readingEntity, FireReadingVO.class);
        vo.setTeamHandle(readingEntity.getTeam().getHandle());
        vo.setTimestamp(vo.getTimestamp().withZoneSameInstant(zoneId));
        vo.add(linkTo(methodOn(FireReadingController.class).findById(vo.getKey(), zoneId.toString())).withSelfRel());

        return vo;
    }

    public long countAllReadings(){
        long count = repository.count();
        return Math.max(count, 1L);
    }
}
