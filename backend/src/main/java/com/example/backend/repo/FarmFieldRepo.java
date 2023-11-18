package com.example.backend.repo;

import com.example.backend.entities.FarmFieldDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmFieldRepo extends JpaRepository<FarmFieldDao, Integer> {
    List<FarmFieldDao> findAll();
}
