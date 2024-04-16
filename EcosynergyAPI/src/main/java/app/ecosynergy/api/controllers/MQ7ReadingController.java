package app.ecosynergy.api.controllers;

import app.ecosynergy.api.data.vo.v1.MQ7ReadingVO;
import app.ecosynergy.api.services.MQ7ReadingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/mq7reading/v1")
public class MQ7ReadingController {
    @Autowired
    MQ7ReadingServices service;

    @GetMapping("/{id}")
    public MQ7ReadingVO findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @GetMapping
    public List<MQ7ReadingVO> findAll(){
        return service.findAll();
    }

    @PostMapping
    public MQ7ReadingVO create(@RequestBody MQ7ReadingVO reading){
        if(reading.getDate() == null){
            reading.setDate(ZonedDateTime.now());
        }
        return service.create(reading);
    }
}
