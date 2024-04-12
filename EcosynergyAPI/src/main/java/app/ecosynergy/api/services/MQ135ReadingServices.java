package app.ecosynergy.api.services;

import app.ecosynergy.api.controllers.MQ135ReadingController;
import app.ecosynergy.api.data.vo.v1.MQ135ReadingVO;
import app.ecosynergy.api.exceptions.RequiredObjectIsNullException;
import app.ecosynergy.api.exceptions.ResourceNotFoundException;
import app.ecosynergy.api.mapper.DozerMapper;
import app.ecosynergy.api.models.MQ135Reading;
import app.ecosynergy.api.repositories.MQ135ReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class MQ135ReadingServices {
    @Autowired
    private MQ135ReadingRepository repository;

    public MQ135ReadingVO findById(Long id){
        if(id == null) throw new RequiredObjectIsNullException();

        MQ135Reading reading = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(""));

        MQ135ReadingVO vo = DozerMapper.parseObject(reading, MQ135ReadingVO.class);
        vo.add(linkTo(methodOn(MQ135ReadingController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public List<MQ135ReadingVO> findAll(){
        List<MQ135Reading> readingsList = repository.findAll();

        List<MQ135ReadingVO> voList = DozerMapper.parseListObjects(readingsList, MQ135ReadingVO.class);
        voList.forEach(vo -> vo.add(linkTo(methodOn(MQ135ReadingController.class).findById(vo.getKey())).withSelfRel()));

        return voList;
    }

    public MQ135ReadingVO create(MQ135ReadingVO reading){
        if(reading == null) throw new RequiredObjectIsNullException();

        MQ135Reading readingEntity = repository.save(DozerMapper.parseObject(reading, MQ135Reading.class));

        MQ135ReadingVO vo = DozerMapper.parseObject(readingEntity, MQ135ReadingVO.class);
        vo.add(linkTo(methodOn(MQ135ReadingController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }
}
