package app.ecosynergy.api.controllers;

import app.ecosynergy.api.data.vo.v1.FireReadingVO;
import app.ecosynergy.api.services.FireReadingServices;
import app.ecosynergy.api.util.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/firereading/v1")
public class FireReadingController {
    @Autowired
    FireReadingServices service;
    
    @GetMapping(value= "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public FireReadingVO findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public List<FireReadingVO> findAll(){
        return service.findAll();
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public FireReadingVO create(@RequestBody FireReadingVO reading){
        if(reading.getDate() == null){
            reading.setDate(ZonedDateTime.now());
        }
        return service.create(reading);
    }
}
