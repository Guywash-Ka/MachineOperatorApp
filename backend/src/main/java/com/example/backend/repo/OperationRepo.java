package com.example.backend.repo;

import com.example.backend.entities.OperationDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepo extends JpaRepository<OperationDao,Integer> {
    List<OperationDao> findAll();
}
