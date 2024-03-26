package app.ecosynergy.api.controllers;

import app.ecosynergy.api.data.vo.v1.FireReadingVO;
import app.ecosynergy.api.services.FireReadingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/firereading/v1")
public class FireReadingController {
    @Autowired
    FireReadingServices service;
    
    @GetMapping(value= "/{id}")
    public FireReadingVO findById(@PathVariable("id") Long id){
        return service.findById(id);
    }

    @GetMapping
    public List<FireReadingVO> findAll(){
        return service.findAll();
    }

    @PostMapping
    public FireReadingVO create(@RequestBody FireReadingVO reading){
        return service.create(reading);
    }
}
