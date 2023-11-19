package com.example.backend.transferClasses;

import com.example.backend.entities.TaskFieldsDao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.In;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateDTO {
    private int id;
    private String title;
    private List<Integer> taskFields;
}
