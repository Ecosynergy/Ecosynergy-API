package app.ecosynergy.api.services;

import app.ecosynergy.api.controllers.MQ7ReadingController;
import app.ecosynergy.api.data.vo.v1.MQ7ReadingVO;
import app.ecosynergy.api.exceptions.RequiredObjectIsNullException;
import app.ecosynergy.api.exceptions.ResourceNotFoundException;
import app.ecosynergy.api.mapper.DozerMapper;
import app.ecosynergy.api.models.MQ7Reading;
import app.ecosynergy.api.repositories.MQ7ReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class MQ7ReadingServices {
    @Autowired
    private MQ7ReadingRepository repository;

    public MQ7ReadingVO findById(Long id){
        if(id == null) throw new RequiredObjectIsNullException();

        MQ7Reading reading = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(""));

        MQ7ReadingVO vo = DozerMapper.parseObject(reading, MQ7ReadingVO.class);
        vo.add(linkTo(methodOn(MQ7ReadingController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public List<MQ7ReadingVO> findAll(){
        List<MQ7Reading> readingsList = repository.findAll();

        List<MQ7ReadingVO> voList = DozerMapper.parseListObjects(readingsList, MQ7ReadingVO.class);
        voList.forEach(vo -> vo.add(linkTo(methodOn(MQ7ReadingController.class).findById(vo.getKey())).withSelfRel()));

        return voList;
    }

    public MQ7ReadingVO create(MQ7ReadingVO reading){
        if(reading == null) throw new RequiredObjectIsNullException();

        MQ7Reading readingEntity = repository.save(DozerMapper.parseObject(reading, MQ7Reading.class));

        MQ7ReadingVO vo = DozerMapper.parseObject(readingEntity, MQ7ReadingVO.class);
        vo.add(linkTo(methodOn(MQ7ReadingController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }
}
