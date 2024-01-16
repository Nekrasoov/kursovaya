package ru.nekrasov.lr8.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.nekrasov.lr8.entity.UserLogs;
import ru.nekrasov.lr8.service.LogService;

import java.util.List;

@Controller
public class LogsController {
    private final LogService logService;

    @Autowired
    public LogsController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/user-logs")
    public String getUserActionLogs(Model model) {
        List<UserLogs> userLogs = logService.getAllUserLogs();
        model.addAttribute("userLogs", userLogs);
        return "user-logs";
    }
}