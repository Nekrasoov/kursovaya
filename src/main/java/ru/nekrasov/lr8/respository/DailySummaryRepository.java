package ru.nekrasov.lr8.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nekrasov.lr8.entity.DailySummary;

import java.time.LocalDate;
import java.util.Optional;

public interface DailySummaryRepository extends JpaRepository<DailySummary, Long> {
    Optional<DailySummary> findByDate(LocalDate date);
}
