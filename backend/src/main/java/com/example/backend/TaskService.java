package com.example.backend;

import com.example.backend.entities.TaskDao;
import com.example.backend.map.MyMapper;
import com.example.backend.repo.AgronomistRepo;
import com.example.backend.repo.TaskRepo;
import com.example.backend.repo.TemplatesRepo;
import com.example.backend.repo.WorkerRepo;
import com.example.backend.transferClasses.TaskDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    TaskRepo taskRepo;
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
}
