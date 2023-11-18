package com.example.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode(exclude = {"tipochelByWorkerId"})
@Table(name = "task", schema = "public", catalog = "postgres")
public class TaskDao {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @JsonIgnore
    @Basic
    @Column(name = "worker_id", nullable = true)
    private Integer workerId;

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.EAGER,
//            cascade = CascadeType.ALL)
//    @JoinColumn(name = "worker_id", referencedColumnName = "id")
//    private TipochelDao tipochelByWorkerId;



}
