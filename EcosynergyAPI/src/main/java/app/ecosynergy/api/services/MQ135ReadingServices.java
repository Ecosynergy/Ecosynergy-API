package app.ecosynergy.api.services;

import app.ecosynergy.api.controllers.MQ135ReadingController;
import app.ecosynergy.api.data.vo.v1.MQ135ReadingVO;
import app.ecosynergy.api.exceptions.RequiredObjectIsNullException;
import app.ecosynergy.api.exceptions.ResourceNotFoundException;
import app.ecosynergy.api.mapper.DozerMapper;
import app.ecosynergy.api.models.MQ135Reading;
import app.ecosynergy.api.models.Team;
import app.ecosynergy.api.repositories.MQ135ReadingRepository;
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
public class MQ135ReadingServices {
    @Autowired
    private MQ135ReadingRepository repository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private PagedResourcesAssembler<MQ135ReadingVO> assembler;

    public MQ135ReadingVO findById(Long id, ZoneId zoneId){
        if(id == null) throw new RequiredObjectIsNullException();

        MQ135Reading reading = repository.findByIdWithTeam(id).orElseThrow(() -> new ResourceNotFoundException(""));

        MQ135ReadingVO vo = DozerMapper.parseObject(reading, MQ135ReadingVO.class);
        vo.setTeamHandle(reading.getTeam().getHandle());
        vo.setTimestamp(vo.getTimestamp().withZoneSameInstant(zoneId));
        vo.add(linkTo(methodOn(MQ135ReadingController.class).findById(vo.getKey(), zoneId.toString())).withSelfRel());
        return vo;
    }

    public PagedModel<EntityModel<MQ135ReadingVO>> findAll(Pageable pageable, ZoneId zoneId){
        Page<MQ135Reading> readingsPage = repository.findAll(pageable);

        Page<MQ135ReadingVO> voPage = readingsPage.map(r -> {
            MQ135ReadingVO vo = DozerMapper.parseObject(r, MQ135ReadingVO.class);
            vo.setTeamHandle(r.getTeam().getHandle());
            vo.setTimestamp(vo.getTimestamp().withZoneSameInstant(zoneId));
            return vo;
        });
        voPage.map(vo -> {
            try{
                return vo.add(linkTo(methodOn(MQ135ReadingController.class).findById(vo.getKey(), zoneId.toString())).withSelfRel());
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        });

        Link link = linkTo(methodOn(MQ135ReadingController.class)
                .findAll(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    pageable.getSort().toString(),
                    zoneId.toString()
                ))
                .withSelfRel();

        return assembler.toModel(voPage, link);
    }

    public MQ135ReadingVO create(MQ135ReadingVO reading, ZoneId zoneId){
        if(reading == null) throw new RequiredObjectIsNullException();

        Team team = DozerMapper.parseObject(
                teamService.findByHandle(reading.getTeamHandle(), zoneId),
                Team.class
        );

        MQ135Reading readingEntity = DozerMapper.parseObject(reading, MQ135Reading.class);
        readingEntity.setTeam(team);
        readingEntity = repository.save(readingEntity);

        MQ135ReadingVO vo = DozerMapper.parseObject(readingEntity, MQ135ReadingVO.class);
        vo.setTeamHandle(readingEntity.getTeam().getHandle());
        vo.setTimestamp(vo.getTimestamp().withZoneSameInstant(zoneId));
        vo.add(linkTo(methodOn(MQ135ReadingController.class).findById(vo.getKey(), zoneId.toString())).withSelfRel());

        return vo;
    }

    public long countAllReadings(){
        long count = repository.count();
        return Math.max(count, 1L);
    }
}
