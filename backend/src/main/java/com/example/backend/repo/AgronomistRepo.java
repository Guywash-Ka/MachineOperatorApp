package com.example.backend.repo;

import com.example.backend.entities.AgronomistDao;
import com.example.backend.entities.WorkerDao;
import org.hibernate.jdbc.WorkExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgronomistRepo extends JpaRepository<AgronomistDao,Integer> {
    AgronomistDao findByNfc(String nfc);
    AgronomistDao findByPassword(String password);
    AgronomistDao findById(int id);
}
