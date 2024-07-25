package app.ecosynergy.api.services;

import app.ecosynergy.api.controllers.MQ7ReadingController;
import app.ecosynergy.api.data.vo.v1.MQ7ReadingVO;
import app.ecosynergy.api.exceptions.RequiredObjectIsNullException;
import app.ecosynergy.api.exceptions.ResourceNotFoundException;
import app.ecosynergy.api.mapper.DozerMapper;
import app.ecosynergy.api.models.MQ7Reading;
import app.ecosynergy.api.models.Team;
import app.ecosynergy.api.repositories.MQ7ReadingRepository;
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
public class MQ7ReadingServices {
    @Autowired
    private MQ7ReadingRepository repository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private PagedResourcesAssembler<MQ7ReadingVO> assembler;

    public MQ7ReadingVO findById(Long id, ZoneId zoneId){
        if(id == null) throw new RequiredObjectIsNullException();

        MQ7Reading reading = repository.findByIdWithTeam(id).orElseThrow(() -> new ResourceNotFoundException(""));

        MQ7ReadingVO vo = DozerMapper.parseObject(reading, MQ7ReadingVO.class);
        vo.setTeamHandle(reading.getTeam().getHandle());
        vo.setTimestamp(reading.getTimestamp().withZoneSameInstant(zoneId));
        vo.add(linkTo(methodOn(MQ7ReadingController.class).findById(vo.getKey(), zoneId.toString())).withSelfRel());
        return vo;
    }

    public PagedModel<EntityModel<MQ7ReadingVO>> findAll(Pageable pageable, ZoneId zoneId){
        Page<MQ7Reading> readingsPage = repository.findAll(pageable);
        Page<MQ7ReadingVO> voPage = readingsPage.map(r -> {
            MQ7ReadingVO vo = DozerMapper.parseObject(r, MQ7ReadingVO.class);
            vo.setTeamHandle(r.getTeam().getHandle());
            vo.setTimestamp(vo.getTimestamp().withZoneSameInstant(zoneId));
            return vo;
        });

        voPage.map(vo -> {
          try{
              return vo.add(linkTo(methodOn(MQ7ReadingController.class).findById(vo.getKey(), zoneId.toString())).withSelfRel());
          } catch (Exception e){
              throw new RuntimeException(e);
          }
        });

        Link link = linkTo(methodOn(MQ7ReadingController.class)
                .findAll(pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSort().toString(),
                        zoneId.toString()
                ))
                .withSelfRel();

        return assembler.toModel(voPage, link);
    }

    public PagedModel<EntityModel<MQ7ReadingVO>> findByTeamHandle(String teamHandle, Pageable pageable, ZoneId zoneId){
        Page<MQ7Reading> readingsPage = repository.findByTeamHandle(teamHandle, pageable);

        Page<MQ7ReadingVO> voPage = readingsPage.map(r -> {
            MQ7ReadingVO vo = DozerMapper.parseObject(r, MQ7ReadingVO.class);
            vo.setTeamHandle(r.getTeam().getHandle());
            vo.setTimestamp(vo.getTimestamp().withZoneSameInstant(zoneId));
            return vo;
        });

        voPage.map(vo -> {
          try{
              return vo.add(linkTo(methodOn(MQ7ReadingController.class).findById(vo.getKey(), zoneId.toString())).withSelfRel());
          } catch (Exception e){
              throw new RuntimeException(e);
          }
        });

        Link link = linkTo(methodOn(MQ7ReadingController.class)
                .findAll(pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSort().toString(),
                        zoneId.toString()
                ))
                .withSelfRel();

        return assembler.toModel(voPage, link);
    }

    public MQ7ReadingVO create(MQ7ReadingVO reading, ZoneId zoneId){
        if(reading == null) throw new RequiredObjectIsNullException();

        Team team = DozerMapper.parseObject(
                teamService.findByHandle(reading.getTeamHandle(), zoneId),
                Team.class
        );

        MQ7Reading readingEntity = DozerMapper.parseObject(reading, MQ7Reading.class);
        readingEntity.setTeam(team);
        readingEntity = repository.save(readingEntity);

        MQ7ReadingVO vo = DozerMapper.parseObject(readingEntity, MQ7ReadingVO.class);
        vo.setTeamHandle(readingEntity.getTeam().getHandle());
        vo.setTimestamp(vo.getTimestamp().withZoneSameInstant(zoneId));
        vo.add(linkTo(methodOn(MQ7ReadingController.class).findById(vo.getKey(), zoneId.toString())).withSelfRel());

        return vo;
    }

    public long countAllReadings(){
        long count = repository.count();
        return Math.max(count, 1L);
    }
}
