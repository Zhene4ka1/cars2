package com.example.exam.impl;

import com.example.exam.dao.StatementDao;
import com.example.exam.model.Role;
import com.example.exam.model.Statement;
import com.example.exam.model.Status;
import com.example.exam.model.User;
import com.example.exam.repo.StatementRepo;
import com.example.exam.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatementImpl implements StatementDao {

    private final StatementRepo statementRepo;
    private final UserRepo userRepo;

    @Override
    public Statement save(Statement statement, String username) {
        User user = userRepo.findByUsername(username);
        statement.setUser(user);
        return statementRepo.save(statement);
    }

    @Override
    public List<Statement> getStatementUser(String username) {
        User user = userRepo.findByUsername(username);
        if(user.getRoles().contains(Role.ADMIN)){
            return statementRepo.findAll();
        }else {
            user = userRepo.findByUsername(username);
            return statementRepo.findByUser(user);
        }
    }

    @Override
    public void updateStatus(Long id, Status status) {
        Statement statement = statementRepo.findById(id)
                .orElseThrow(() -> new IllegalThreadStateException("Не найден id заявки"));
        statement.setStatus(status);
        statementRepo.save(statement);
    }
}
