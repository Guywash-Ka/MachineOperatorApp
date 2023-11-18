package com.example.backend.entities;

import com.example.backend.transferClasses.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Objects;

@Entity
@Data
@Table(name = "agronomist", schema = "public"/*, catalog = "AgroDB"*/)
public class AgronomistDao extends User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = true, length = -1)
    private String name;
    @Basic
    @Column(name = "password", nullable = true, length = -1)
    private String password;
    @Basic
    @Column(name = "nfc", nullable = true, length = -1)
    private String nfc;

}
