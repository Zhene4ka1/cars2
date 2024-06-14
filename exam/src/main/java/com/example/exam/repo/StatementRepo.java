package com.example.exam.repo;

import com.example.exam.model.Statement;
import com.example.exam.model.Status;
import com.example.exam.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatementRepo extends JpaRepository<Statement,Long> {
    List<Statement> findByUser(User user);
    List<Statement> findByStatus(Status status);
}
