package ru.nekrasov.lr8.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nekrasov.lr8.entity.UserLogs;

public interface LogRepository extends JpaRepository<UserLogs, Long> {
}
