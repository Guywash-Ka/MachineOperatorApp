package com.example.backend.entities;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "farm_field", schema = "public"/*, catalog = "AgroDB"*/)
public class FarmFieldDao {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = true, length = -1)
    private String name;


}
