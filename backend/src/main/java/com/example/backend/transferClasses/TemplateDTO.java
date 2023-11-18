package com.example.backend.transferClasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemplateDTO {
    String name;
    List<Integer> fields;
}
