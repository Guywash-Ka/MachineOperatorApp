package com.example.backend.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "agregat", schema = "public"/*, catalog = "AgroDB"*/)
public class AgregatDao {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = true, length = -1)
    private String name;


}
