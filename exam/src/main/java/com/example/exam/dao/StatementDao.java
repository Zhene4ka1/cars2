package com.example.exam.dao;

import com.example.exam.model.Statement;
import com.example.exam.model.Status;

import java.util.List;

public interface StatementDao {
    Statement save(Statement statement, String username);
    List<Statement> getStatementUser(String username);
    void updateStatus(Long id, Status status);
}
