package app.ecosynergy.api.services;

import app.ecosynergy.api.controllers.FireReadingController;
import app.ecosynergy.api.data.vo.v1.FireReadingVO;
import app.ecosynergy.api.exceptions.RequiredObjectIsNullException;
import app.ecosynergy.api.exceptions.ResourceNotFoundException;
import app.ecosynergy.api.mapper.DozerMapper;
import app.ecosynergy.api.models.FireReading;
import app.ecosynergy.api.repositories.FireReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class FireReadingServices {
    @Autowired
    private FireReadingRepository repository;

    public FireReadingVO findById(Long id){
        if(id == null) throw new RequiredObjectIsNullException();

        FireReading reading = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(""));

        FireReadingVO vo = DozerMapper.parseObject(reading, FireReadingVO.class);
        vo.add(linkTo(methodOn(FireReadingController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public List<FireReadingVO> findAll(){
        List<FireReading> readingsList = repository.findAll();

        List<FireReadingVO> voList = DozerMapper.parseListObjects(readingsList, FireReadingVO.class);
        voList.forEach(vo -> vo.add(linkTo(methodOn(FireReadingController.class).findById(vo.getKey())).withSelfRel()));

        return voList;
    }

    public FireReadingVO create(FireReadingVO reading){
        if(reading == null) throw new RequiredObjectIsNullException();

        FireReading readingEntity = repository.save(DozerMapper.parseObject(reading, FireReading.class));

        FireReadingVO vo = DozerMapper.parseObject(readingEntity, FireReadingVO.class);
        vo.add(linkTo(methodOn(FireReadingController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }
}
