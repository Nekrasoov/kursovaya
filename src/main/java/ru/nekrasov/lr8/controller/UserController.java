package ru.nekrasov.lr8.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.nekrasov.lr8.service.LogService;
import ru.nekrasov.lr8.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private LogService logService;
    @Autowired
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/readonly-resource")
    public ModelAndView getReadonlyResource() {
        ModelAndView mav = new ModelAndView("readonly-resource");
        mav.addObject("users", userService.findAllUsers());
        return mav;
    }

    @PostMapping("/set-new-roles")
    public String setNewRoles(@RequestParam("userId") Long userId,
                              @RequestParam("roleName") String roleName) {
        userService.setNewRoles(userId, roleName);
        return "redirect:/user/readonly-resource"; // Перенаправление на другую страницу после выполнения операции
    }





}
