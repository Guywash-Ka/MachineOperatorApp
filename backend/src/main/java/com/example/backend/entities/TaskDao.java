package com.example.backend.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;

//import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
//import org.springframework.data.relational.core.mapping.Column;
//import org.hibernate.loader.ast.spi.SqlArrayMultiKeyLoader;
import org.springframework.data.relational.core.dialect.PostgresDialect;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task", schema = "public"/*, catalog = "AgroDB"*/)
public class TaskDao {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
//    @Basic
//    @Column(name = "agronom_id", nullable = true)
//    private Integer agronomId;
//    @Basic
//    @Column(name = "worker_id", nullable = true)
//    private Integer workerId;
//    @Basic
//    @Column(name = "template_id", nullable = true)
//    private Integer templateId;
//    @Basic



    @Column(name = "arr", columnDefinition = "varying[]")
//    @Convert(converter = Converter.class)
    @ColumnTransformer(write = "?::varchar      []")
    private String arr;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private AgronomistDao agronom;
    @ManyToOne
    private WorkerDao worker;
    @ManyToOne
    private TemplatesDao template;




}
