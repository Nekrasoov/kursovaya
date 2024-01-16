package ru.nekrasov.lr8.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DAILY_SUMMARY")
public class DailySummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "date", unique = true)
    private LocalDate date;

    @Column(name = "total_price")
    private int totalPrice;

    public DailySummary(LocalDate date, int i) {

    }
}
