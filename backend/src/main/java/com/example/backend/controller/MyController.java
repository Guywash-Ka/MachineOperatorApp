package com.example.backend.controller;

import com.example.backend.entities.*;
import com.example.backend.repo.AgronomistRepo;
import com.example.backend.repo.WorkerRepo;
import com.example.backend.transferClasses.RoleAndId;
import com.example.backend.transferClasses.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Objects;

@RestController
public class MyController {

    @Autowired
    WorkerRepo workerRepo;
    @Autowired
    AgronomistRepo agronomistRepo;


    @GetMapping("/getRoleAndIdByNfc/{nfc}")
    public ResponseEntity<RoleAndId> getByNfc(@PathVariable(name = "nfc")String nfc) {

        WorkerDao wdao= workerRepo.findByNfc(nfc);
        if (wdao!=null){
            return new ResponseEntity<>(new RoleAndId(wdao.getId(),"worker"),HttpStatus.OK);
        }
        AgronomistDao adao =agronomistRepo.findByNfc(nfc);
        if (adao!=null){
            return new ResponseEntity<>(new RoleAndId(adao.getId(),"agronom"),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }
    @PostMapping("/saveWorker")
    public ResponseEntity<WorkerDao> saveWorker(@RequestBody WorkerDao wdao){

        return new ResponseEntity<>(workerRepo.save(wdao),HttpStatus.OK);
    }
    @PostMapping("/saveAgronom")
    public ResponseEntity<AgronomistDao> saveAgronom(@RequestBody AgronomistDao adao){

        return new ResponseEntity<>(agronomistRepo.save(adao),HttpStatus.OK);
    }
    @GetMapping("/getUser")
    public ResponseEntity<User> getUserById(@RequestParam int id,@RequestParam String role){

        if (Objects.equals(role, "worker")){
            User dao =workerRepo.findById(id);
            return new ResponseEntity<>(dao, HttpStatus.OK);
        }
        if (Objects.equals(role, "agronom")){
            User dao =agronomistRepo.findById(id);
            return new ResponseEntity<>(dao, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @GetMapping("/getRoleAndIdByPassword/{password}")
    ResponseEntity<RoleAndId> getByPassword(@PathVariable(name="password") String password){
        WorkerDao wdao= workerRepo.findByPassword(password);
        if (wdao!=null){
            return new ResponseEntity<>(new RoleAndId(wdao.getId(),"worker"),HttpStatus.OK);
        }
        AgronomistDao adao =agronomistRepo.findByPassword(password);
        if (adao!=null){
            return new ResponseEntity<>(new RoleAndId(adao.getId(),"agronom"),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
//    @PutMapping("/updateSalary")
//    ResponseEntity<>












}
