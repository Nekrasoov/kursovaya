package ru.nekrasov.lr8.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.nekrasov.lr8.entity.Sale;
import ru.nekrasov.lr8.respository.DailySummaryRepository;
import ru.nekrasov.lr8.respository.SaleRepository;
import ru.nekrasov.lr8.service.LogService;
import ru.nekrasov.lr8.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class SaleController {
    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private DailySummaryRepository dailySummaryRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;

    @GetMapping("/list")
    public ModelAndView getAllSale(){
        log.info("/list -> connection");
        ModelAndView mav = new ModelAndView("list-sale");
        mav.addObject("sale", saleRepository.findAll());
        logService.logAction(saleRepository.toString(), "Список продаж");
        return mav;
    }
    @GetMapping("/Daily")
    public ModelAndView getAllDaily(){
        log.info("/Daily -> connection");
        ModelAndView mav = new ModelAndView("list-total");
        mav.addObject("daily", dailySummaryRepository.findAll());
        logService.logAction(dailySummaryRepository.toString(), "Открыл сумму по дням");
        return mav;
    }


    @GetMapping("/addSaleForm")
    public ModelAndView addSaleForm(){
        ModelAndView mav = new ModelAndView("add-sale-form");
        Sale sale = new Sale();
        mav.addObject("sale", sale);
        logService.logAction(saleRepository.toString(), "Список продаж");
        return mav;
    }

    @PostMapping("/saveSale")
    public String saveSale(@ModelAttribute Sale sale){
        saleRepository.save(sale);
        logService.logAction(saleRepository.toString(), "Сохранил");
        return "redirect:/list";


    }


    @GetMapping("/showUpdateForm")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ModelAndView showUpdateForm(@RequestParam Long saleId){
        ModelAndView mav = new ModelAndView("add-sale-form");
        logService.logAction(saleRepository.toString(), "Доступ к кнопкам");
        Optional<Sale> optionalSale = saleRepository.findById(saleId);
        Sale sale = new Sale();
        if (optionalSale.isPresent()){
            sale = optionalSale.get();
        }
        mav.addObject("sale", sale);
        return mav;
    }
    @GetMapping("/deleteSale")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public String deleteSale(@RequestParam Long saleId){
        saleRepository.deleteById(saleId);
        logService.logAction(saleRepository.toString(), "Доступ к кнопкам");
        return "redirect:/list";
    }

    @ModelAttribute("club")
    public List<String> getAllClubs() {
        return Arrays.asList("Мега", "ФанФан", "Вайнера");
    }

    @GetMapping("/readonly-resource")
    @Secured("ROLE_READONLY")
    public ModelAndView getReadonlyResource() {
        ModelAndView mav = new ModelAndView("readonly-resource");
        mav.addObject("users", userService.findAllUsers());
        logService.logAction(userService.toString(), "Рид-Онли");
        return mav;
    }

}
