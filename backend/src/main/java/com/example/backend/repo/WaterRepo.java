package com.example.backend.repo;

import com.example.backend.entities.WaterDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaterRepo extends JpaRepository<WaterDao,Integer> {
    List<WaterDao> findAll();
}
