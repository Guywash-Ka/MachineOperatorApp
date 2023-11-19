package com.example.backend.controller;

import com.example.backend.entities.*;
import com.example.backend.repo.*;
import jdk.dynalink.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FieldsController {
    @Autowired
    AgregatRepo agregatRepo;
    @Autowired
    FarmFieldRepo farmFieldRepo;
    @Autowired
    OperationRepo operationRepo;
    @Autowired
    TransportRepo transportRepo;
    @Autowired
    WaterRepo waterRepo;

    @GetMapping("/getAgregat/{id}")
    public AgregatDao getAgregatById(@PathVariable(name = "id")int id){
        return agregatRepo.findById(id);
    }
    @GetMapping("/getFarmField/{id}")
    public FarmFieldDao getFarmFieldById(@PathVariable(name = "id")int id){
        return farmFieldRepo.findById(id);
    }
    @GetMapping("/getOperation/{id}")
    public OperationDao getOperationById(@PathVariable(name = "id")int id){
        return operationRepo.findById(id);
    }
    @GetMapping("/getTransport/{id}")
    public TransportDao getTransportById(@PathVariable(name = "id")int id){
        return transportRepo.findById(id);
    }
    @GetMapping("/getWater/{id}")
    public WaterDao getWaterById(@PathVariable(name = "id")int id){
        return waterRepo.findById(id);
    }

}
