package com.example.backend.service;

import com.example.backend.entities.TaskDao;
import com.example.backend.map.MyMapper;
import com.example.backend.repo.*;
import com.example.backend.transferClasses.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepo taskRepo;
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

    @Autowired
    TemplatesRepo templatesRepo;
    @Autowired
    AgronomistRepo agronomistRepo;
    @Autowired
    WorkerRepo workerRepo;
    @Autowired
    MyMapper mapper;

    public boolean saveTask(TaskDto taskDto){
        TaskDao dao=mapper.TaskDtoToDao(taskDto);
        dao.setAgronom(agronomistRepo.findById(taskDto.getAgronomId()).get());
        dao.setWorker(workerRepo.findById(taskDto.getWorkerId()).get());
        dao.setTemplate(templatesRepo.findById(taskDto.getTemplateId()).get());

        if (taskDto.getArr().size()!=dao.getTemplate().getTaskFields().size()){
            return false;
        }
        taskRepo.save(dao);
        return true;

    }

    String setFill(String name,int id){
        switch (name){
            case "Агрегат":
                return agregatRepo.findById(id).getName();
            case "Фермерское Поле":
                return farmFieldRepo.findById(id).getName();
            case "Операция":
                return operationRepo.findById(id).getName();
            case "Транспорт":
                return transportRepo.findById(id).getName();
            case "Раствор":
                return waterRepo.findById(id).getName();
            case "Скорость":
                return String.valueOf(id);
            case "Глубина":
                return String.valueOf(id);
            default:
                return String.valueOf(0);

        }
    }
    public List<String> getFillings( TaskDao dao){
        var fields=dao.getTemplate().getTaskFields();
        TaskDto taskDto=mapper.TaskDaoToDto(dao);
        List<String> strings=new ArrayList<>();
        for (int i = 0; i < fields.size(); i++) {
            var f=fields.get(i);
            strings.add(setFill(f.getName(),taskDto.getArr().get(i)));

        }
        return strings;

    }
}
