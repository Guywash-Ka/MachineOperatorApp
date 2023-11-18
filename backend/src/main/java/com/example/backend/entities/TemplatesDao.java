package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
//@Getter
//@Setter
@Data
//@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
//@ToString(exclude = {"taskFields"})`
//@EqualsAndHashCode(exclude = {"taskFields"})`
@Table(name = "templates", schema = "public"/*, catalog = "AgroDB"*/)
public class TemplatesDao {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "title", nullable = true, length = -1)
    private String title;
    @ManyToMany(cascade = {CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinTable(
            name = "templates_task_fields",
            joinColumns = { @JoinColumn(name = "templates_id") },
            inverseJoinColumns = { @JoinColumn(name = "task_fields_id") }
    )
    private List<TaskFieldsDao> taskFields=new ArrayList<>();

    public TemplatesDao(String title, List<TaskFieldsDao> taskFields) {
        this.title = title;
        this.taskFields = taskFields;
    }

}
