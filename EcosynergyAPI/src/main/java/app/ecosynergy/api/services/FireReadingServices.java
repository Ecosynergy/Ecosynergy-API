package app.ecosynergy.api.services;

import app.ecosynergy.api.controllers.FireReadingController;
import app.ecosynergy.api.data.vo.v1.FireReadingVO;
import app.ecosynergy.api.exceptions.RequiredObjectIsNullException;
import app.ecosynergy.api.exceptions.ResourceNotFoundException;
import app.ecosynergy.api.mapper.DozerMapper;
import app.ecosynergy.api.models.FireReading;
import app.ecosynergy.api.repositories.FireReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class FireReadingServices {
    @Autowired
    private FireReadingRepository repository;

    @Autowired
    private PagedResourcesAssembler<FireReadingVO> assembler;

    public FireReadingVO findById(Long id){
        if(id == null) throw new RequiredObjectIsNullException();

        FireReading reading = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(""));

        FireReadingVO vo = DozerMapper.parseObject(reading, FireReadingVO.class);
        vo.add(linkTo(methodOn(FireReadingController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PagedModel<EntityModel<FireReadingVO>> findAll(Pageable pageable){
        Page<FireReading> readingsPage = repository.findAll(pageable);

        Page<FireReadingVO> voPage = readingsPage.map(r -> DozerMapper.parseObject(r, FireReadingVO.class));
        voPage.map(vo -> {
            try{
                return vo.add(linkTo(methodOn(FireReadingController.class).findById(vo.getKey())).withSelfRel());
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        });

        Link link = linkTo(methodOn(FireReadingController.class)
                .findAll(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSort().toString()
                ))
                .withSelfRel();

        return assembler.toModel(voPage, link);
    }

    public FireReadingVO create(FireReadingVO reading){
        if(reading == null) throw new RequiredObjectIsNullException();

        FireReading readingEntity = repository.save(DozerMapper.parseObject(reading, FireReading.class));

        FireReadingVO vo = DozerMapper.parseObject(readingEntity, FireReadingVO.class);
        vo.add(linkTo(methodOn(FireReadingController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public long countAllReadings(){
        long count = repository.count();
        return Math.max(count, 1L);
    }
}
