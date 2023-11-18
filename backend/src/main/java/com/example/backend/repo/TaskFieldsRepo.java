package com.example.backend.repo;

import com.example.backend.entities.TaskFieldsDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskFieldsRepo extends JpaRepository<TaskFieldsDao,Integer> {
    TaskFieldsDao findById(int id);
}
