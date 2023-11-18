package com.example.backend.repo;

import com.example.backend.entities.AgronomistDao;
import com.example.backend.entities.WorkerDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerRepo  extends JpaRepository<WorkerDao,Integer> {
    WorkerDao findByNfc(String nfc);
    WorkerDao findByPassword(String password);
    WorkerDao findById(int i);
}
