package com.example.backend.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tasks", schema = "public", catalog = "AgroDB")
public class TasksDao {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = true, length = -1)
    private String name;
    @Basic
    @Column(name = "description", nullable = true, length = -1)
    private String description;
    @Basic
    @Column(name = "cost", nullable = true, precision = 0)
    private Double cost;

    @Basic
    @Column(name = "is_done", nullable = true)
    private Boolean isDone;
    @ManyToOne
    @JoinColumn(name = "worker_id", referencedColumnName = "id")
    private WorkerDao workerByWorkerId;
}
