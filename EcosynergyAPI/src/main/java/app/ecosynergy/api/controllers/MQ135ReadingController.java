package app.ecosynergy.api.controllers;

import app.ecosynergy.api.data.vo.v1.MQ135ReadingVO;
import app.ecosynergy.api.services.MQ135ReadingServices;
import app.ecosynergy.api.util.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/mq135reading/v1")
public class MQ135ReadingController {
    @Autowired
    MQ135ReadingServices service;

    @GetMapping(value = "/{id}",
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public MQ135ReadingVO findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public List<MQ135ReadingVO> findAll(){
        return service.findAll();
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}
    )
    public MQ135ReadingVO create(@RequestBody MQ135ReadingVO reading){
        if(reading.getDate() == null){
            reading.setDate(ZonedDateTime.now());
        }
        return service.create(reading);
    }
}
