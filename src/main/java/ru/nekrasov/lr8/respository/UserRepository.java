package ru.nekrasov.lr8.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nekrasov.lr8.entity.User;

public interface UserRepository  extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
