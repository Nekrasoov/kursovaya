package ru.nekrasov.lr8.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class DailySummaryDTO {
    private LocalDate date;
    private int totalPrice;
}
