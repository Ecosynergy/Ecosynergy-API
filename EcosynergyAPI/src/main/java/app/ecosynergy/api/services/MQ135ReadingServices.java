package app.ecosynergy.api.services;

import app.ecosynergy.api.controllers.MQ135ReadingController;
import app.ecosynergy.api.data.vo.v1.MQ135ReadingVO;
import app.ecosynergy.api.exceptions.RequiredObjectIsNullException;
import app.ecosynergy.api.exceptions.ResourceNotFoundException;
import app.ecosynergy.api.mapper.DozerMapper;
import app.ecosynergy.api.models.MQ135Reading;
import app.ecosynergy.api.repositories.MQ135ReadingRepository;
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
public class MQ135ReadingServices {
    @Autowired
    private MQ135ReadingRepository repository;

    @Autowired
    private PagedResourcesAssembler<MQ135ReadingVO> assembler;

    public MQ135ReadingVO findById(Long id){
        if(id == null) throw new RequiredObjectIsNullException();

        MQ135Reading reading = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(""));

        MQ135ReadingVO vo = DozerMapper.parseObject(reading, MQ135ReadingVO.class);
        vo.add(linkTo(methodOn(MQ135ReadingController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public PagedModel<EntityModel<MQ135ReadingVO>> findAll(Pageable pageable){
        Page<MQ135Reading> readingsPage = repository.findAll(pageable);

        Page<MQ135ReadingVO> voPage = readingsPage.map(r -> DozerMapper.parseObject(r, MQ135ReadingVO.class));
        voPage.map(vo -> {
            try{
                return vo.add(linkTo(methodOn(MQ135ReadingController.class).findById(vo.getKey())).withSelfRel());
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        });

        Link link = linkTo(methodOn(MQ135ReadingController.class)
                .findAll(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    pageable.getSort().toString()
                ))
                .withSelfRel();

        return assembler.toModel(voPage, link);
    }

    public MQ135ReadingVO create(MQ135ReadingVO reading){
        if(reading == null) throw new RequiredObjectIsNullException();

        MQ135Reading readingEntity = repository.save(DozerMapper.parseObject(reading, MQ135Reading.class));

        MQ135ReadingVO vo = DozerMapper.parseObject(readingEntity, MQ135ReadingVO.class);
        vo.add(linkTo(methodOn(MQ135ReadingController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public long countAllReadings(){
        return repository.count();
    }
}
