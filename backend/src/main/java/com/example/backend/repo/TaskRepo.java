package com.example.backend.repo;

import com.example.backend.entities.TaskDao;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepo extends JpaRepository<TaskDao,Integer> {
//    @Modifying
//    @Transactional
//    @Query("update TaskDao t set t.workerId = ?1  where t.id = ?2")
//    Integer setWorker(int workerId,int taskId);
//    TaskDao findFirstByOrderAtDesc();
    TaskDao findFirstByOrderByIdDesc();
    List<TaskDao> findAllByWorkerId(int id);
    List<TaskDao> findAllByIdAfter(int id);

    TaskDao findById(int id);
}
