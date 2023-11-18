package com.example.backend.transferClasses;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Integer id;
    private Integer agronomId;
    private Integer workerId;
    private Integer templateId;
    private List<Integer> arr;
}
