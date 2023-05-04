package ru.skypro.homework.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Avatar;

@Primary
@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
}
