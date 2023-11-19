package com.example.backend.repo;

import com.example.backend.entities.TemplatesDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface TemplatesRepo extends JpaRepository<TemplatesDao, Integer> {
    TemplatesDao findById(int id);
    List<TemplatesDao> findAll();
//    Integer ();
}
