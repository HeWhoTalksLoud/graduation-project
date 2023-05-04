package ru.skypro.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.model.Avatar;
import ru.skypro.homework.repository.AvatarRepository;

@Service
@AllArgsConstructor
public class AvatarService {

    private final AvatarRepository repository;

    public byte[] getImageAsBytes(Long imageId) {
        Avatar picture = repository.findById(imageId)
                .orElseThrow(() -> new NotFoundException("Аватар с ид. " + imageId + " не найден"));
        return picture.getData();
    }
}
