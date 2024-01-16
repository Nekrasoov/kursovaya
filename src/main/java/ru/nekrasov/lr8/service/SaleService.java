package ru.nekrasov.lr8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nekrasov.lr8.dto.DailySummaryDTO;
import ru.nekrasov.lr8.entity.DailySummary;
import ru.nekrasov.lr8.entity.Sale;
import ru.nekrasov.lr8.respository.DailySummaryRepository;
import ru.nekrasov.lr8.respository.SaleRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private DailySummaryRepository dailySummaryRepository;


    public void updateDailySummary() {
        List<Sale> sales = saleRepository.findAll();

        Map<LocalDate, Integer> dailySummaries = sales.stream()
                .collect(Collectors.groupingBy(Sale::getDate,
                        Collectors.summingInt(Sale::getPrice)));

        dailySummaries.forEach((date, totalPrice) -> {
            DailySummary dailySummary = dailySummaryRepository.findByDate(date)
                    .orElseGet(() -> new DailySummary(date, 0));

            dailySummary.setTotalPrice(totalPrice);

            dailySummaryRepository.save(dailySummary);
        });
    }

    public List<DailySummaryDTO> getSumPricesByDate() {
        return dailySummaryRepository.findAll().stream()
                .map(dailySummary -> new DailySummaryDTO(dailySummary.getDate(), dailySummary.getTotalPrice()))
                .collect(Collectors.toList());
    }
}
