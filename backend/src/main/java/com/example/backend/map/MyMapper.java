package com.example.backend.map;

import com.example.backend.entities.AgronomistDao;
import com.example.backend.entities.TaskDao;
import com.example.backend.entities.TemplatesDao;
import com.example.backend.entities.WorkerDao;
import com.example.backend.transferClasses.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
@Component/*(value = "mapper")*/
public interface MyMapper {
    @Mapping(source = "arr", target = "arr", qualifiedByName = "ArrToObj")
    TaskDao TaskDtoToDao(TaskDto dto);
    @Mapping(source = "arr", target = "arr", qualifiedByName = "objToArr")
    @Mapping(source = "agronom", target = "agronomId", qualifiedByName = "agronomToId")
    @Mapping(source = "worker", target = "workerId", qualifiedByName = "workerToId")
    @Mapping(source = "template", target = "templateId", qualifiedByName = "templateToId")
    TaskDto TaskDaoToDto(TaskDao dao);

    List<TaskDto> TaskDaoListToDtoList(List<TaskDao> daos);

    @Named("objToArr")
    public static List<Integer> objToArr(String object) {

        String res=object.replace("{","");
        res=res.replace("}","");
        List<String> strs= List.of(res.split(","));
        List<Integer> ints= new ArrayList<>();
        for (int i = 0; i < strs.size(); i++) {
            ints.add(Integer.valueOf(strs.get(i)));

        }

        return ints;
    }
    @Named("ArrToObj")
    public static String objToArr(List<Integer> arr) {
        StringBuilder a= new StringBuilder();
        a.append("{");
        for (int i = 0; i < arr.size(); i++) {

            a.append(arr.get(i));
            if (i!=arr.size()-1){
                a.append(",");
            }
        }
        a.append("}");

        return a.toString();
    }
    @Named("agronomToId")
    public static Integer obj1ToArr(AgronomistDao object) {
        return object.getId();
    }
    @Named("workerToId")
    public static Integer obj2ToArr(WorkerDao object) {
        return object.getId();
    }
    @Named("templateToId")
    public static Integer obj3ToArr(TemplatesDao object) {
        return object.getId();
    }



}
