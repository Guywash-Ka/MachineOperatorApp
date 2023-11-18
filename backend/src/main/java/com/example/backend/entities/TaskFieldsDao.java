package com.example.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@ToString(exclude = {"templates"})
@EqualsAndHashCode(exclude = {"templates"})
@AllArgsConstructor
//@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "task_fields", schema = "public"/*, catalog = "AgroDB"*/)
public class TaskFieldsDao {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = true, length = -1)
    private String name;
    @JsonIgnore
    @ManyToMany(mappedBy = "taskFields",fetch = FetchType.EAGER)
    private List<TemplatesDao> templates=new ArrayList<>();

    public TaskFieldsDao(String name) {

        this.name = name;
    }
}
