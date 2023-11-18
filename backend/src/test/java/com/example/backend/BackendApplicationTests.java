package com.example.backend;

import com.example.backend.repo.TaskFieldsRepo;
import com.example.backend.repo.TemplatesRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {
    @Autowired
    TemplatesRepo templatesRepo;
    @Autowired
    TaskFieldsRepo taskFieldsRepo;

    @Test
    void contextLoads() {
    }

    @Test
    void tt(){
//        List<TaskFieldsDao> l=new ArrayList<>();
//        l.add(taskFieldsRepo.findById(1));
//        l.add(taskFieldsRepo.findById(2));
//        var a= new TemplatesDao("sndTemp",l);
//        templatesRepo.saveAndFlush(a);

        System.out.println(templatesRepo.findAll());
    }

}
