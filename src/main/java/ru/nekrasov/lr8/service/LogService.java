package ru.nekrasov.lr8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nekrasov.lr8.entity.UserLogs;
import ru.nekrasov.lr8.respository.LogRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogService {
    private final LogRepository logRepository;

    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public List<UserLogs> getAllUserLogs() {
        return logRepository.findAll();
    }
    public void logAction(String username, String action) {
        UserLogs userLogs = new UserLogs();
        userLogs.setUsername(username);
        userLogs.setAction(action);
        userLogs.setTimestamp(LocalDateTime.now());
        logRepository.save(userLogs);
    }
}