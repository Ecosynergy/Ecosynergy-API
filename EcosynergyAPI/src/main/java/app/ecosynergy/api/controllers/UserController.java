package app.ecosynergy.api.controllers;

import app.ecosynergy.api.data.vo.v1.UserVO;
import app.ecosynergy.api.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/v1")
public class UserController {
    @Autowired
    UserServices services;
    @GetMapping(value = "/{id}")
    public UserVO findById(@PathVariable(name = "id") Long id){
        return services.findById(id);
    }
    
    @GetMapping
    public List<UserVO> findAll(){
        return services.findAll();
    }

    @PutMapping
    public UserVO update(@RequestBody UserVO user){
        return services.update(user);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
        services.delete(id);
        return ResponseEntity.noContent().build();
    }
}
