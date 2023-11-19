package com.example.backend.controller;

import com.example.backend.service.TaskService;
import com.example.backend.entities.*;
import com.example.backend.map.MyMapper;
import com.example.backend.repo.*;
import com.example.backend.transferClasses.Docs;
import com.example.backend.transferClasses.TaskDto;
import com.example.backend.transferClasses.TemplateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskController {
    @Autowired
    TemplatesRepo templatesRepo;
    @Autowired
    TaskFieldsRepo taskFieldsRepo;
    @Autowired
    AgronomistRepo agronomistRepo;
    @Autowired
    WorkerRepo workerRepo;
    @Autowired
    AgregatRepo agregatRepo;
    @Autowired
    TaskRepo taskRepo;
    @Autowired
    FarmFieldRepo farmFieldRepo;
    @Autowired
    OperationRepo operationRepo;
    @Autowired
    TransportRepo transportRepo;
    @Autowired
    WaterRepo waterRepo;
    @Autowired
    TaskService taskService;

    //    @Qualifier/*(value = "mapper")*/
    @Autowired
    MyMapper mapper;


    @GetMapping("/getTemplatesRodya")
    public ResponseEntity<List<TemplateDTO>> getTemplates() {
        List<TemplatesDao> templatesDaos = templatesRepo.findAll();

        return new ResponseEntity<>(mapper.tempListDAOtoDTO(templatesDaos), HttpStatus.OK);
    }

    @GetMapping("/getTemplatesById/{id}")
    public ResponseEntity<TemplatesDao> getTemplatesById(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(templatesRepo.findById(id), HttpStatus.OK);
    }

    @GetMapping("/getTaskFields")
    public ResponseEntity<List<TaskFieldsDao>> getTaskFields() {
        return new ResponseEntity<>(taskFieldsRepo.findAll(), HttpStatus.OK);
    }

    @GetMapping("/getTaskFields/{id}")
    public ResponseEntity<TaskFieldsDao> getTaskFieldsById(@PathVariable(name = "id") int id) {
        return new ResponseEntity<>(taskFieldsRepo.findById(id), HttpStatus.OK);
    }

    @PostMapping("/saveTaskFields")
    public ResponseEntity<TaskFieldsDao> saveTaskField(@RequestBody TaskFieldsDao dao) {
        taskFieldsRepo.save(dao);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/saveTemplate")
    public ResponseEntity<TemplatesDao> saveTemplate(@RequestBody TemplateDTO dto) {
        List<Integer> fields = dto.getTaskFields();
        List<TaskFieldsDao> taskFieldsDaos = new ArrayList<>();
        for (Integer field : fields) {
            taskFieldsDaos.add(taskFieldsRepo.findById(field).get());
        }
        TemplatesDao templatesDao = new TemplatesDao(dto.getTitle(), taskFieldsDaos);
        templatesRepo.saveAndFlush(templatesDao);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/saveTask")
    public ResponseEntity<TaskDao> saveTask(@RequestBody TaskDto dto) {
        if (taskService.saveTask(dto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getTaskByTaskId/{id}")
    public ResponseEntity<TaskDto> getTask(@PathVariable(name = "id") int id) {
        TaskDto dto = mapper.TaskDaoToDto(taskRepo.findById(id));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping("/getLastTaskId")
    public ResponseEntity<Integer> getLastId() {
        TaskDao dao = taskRepo.findFirstByOrderByIdDesc();
        return new ResponseEntity<>(dao.getId(), HttpStatus.OK);
    }

    @GetMapping("/getAllAfterId/{id}")
    public ResponseEntity<List<TaskDto>> getAllAfterId(@PathVariable(name = "id") int id) {
        List<TaskDto> res = mapper.TaskDaoListToDtoList(taskRepo.findAllByIdAfter(id));
        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @GetMapping("/getAll/{stringId}")
    public ResponseEntity<Object> getAllByStringID(@PathVariable(name = "stringId") String id) {

        return switch (id) {
            case "Agronoms" -> new ResponseEntity<>(agronomistRepo.findAll(), HttpStatus.OK);
            case "Agregats" -> new ResponseEntity<>(agregatRepo.findAll(), HttpStatus.OK);
            case "FarmFields" -> new ResponseEntity<>(farmFieldRepo.findAll(), HttpStatus.OK);
            case "Operations" -> new ResponseEntity<>(operationRepo.findAll(), HttpStatus.OK);
            case "TaskFields" -> new ResponseEntity<>(taskFieldsRepo.findAll(), HttpStatus.OK);
            case "Tasks" -> new ResponseEntity<>(mapper.TaskDaoListToDtoList(taskRepo.findAll()), HttpStatus.OK);
            case "Templates" -> new ResponseEntity<>(templatesRepo.findAll(), HttpStatus.OK);
            case "Transport" -> new ResponseEntity<>(transportRepo.findAll(), HttpStatus.OK);
            case "Waters" -> new ResponseEntity<>(waterRepo.findAll(), HttpStatus.OK);
            case "Workers" -> new ResponseEntity<>(workerRepo.findAll(), HttpStatus.OK);
            default -> new ResponseEntity<>(HttpStatus.NOT_FOUND);
        };

    }

    @GetMapping("/getTaskFillingById/{id}")
    public ResponseEntity<List<String>> getTaskFilling(@PathVariable(name = "id") int id) {
        TaskDao dao = taskRepo.findById(id);

        return new ResponseEntity<>(taskService.getFillings(dao), HttpStatus.OK);
    }

//
    @GetMapping("/getDocs")
    public ResponseEntity<List<Docs>> getDocs () {
        List<Docs> docs=mapper.AgroListToDocs(agronomistRepo.findAll());
        docs.addAll(mapper.WorkListToDocs(workerRepo.findAll()));
        return new ResponseEntity<>(docs,HttpStatus.OK);

    }


}
