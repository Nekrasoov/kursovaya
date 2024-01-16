package ru.nekrasov.lr8.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nekrasov.lr8.entity.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}