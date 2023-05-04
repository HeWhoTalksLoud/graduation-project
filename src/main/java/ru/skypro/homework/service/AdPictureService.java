package ru.skypro.homework.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.model.AdPicture;
import ru.skypro.homework.repository.AdPictureRepository;

@Service
@AllArgsConstructor
public class AdPictureService {

    private final AdPictureRepository repository;

    public byte[] getImageAsBytes(Long imageId) {
        AdPicture picture = repository.findById(imageId)
                .orElseThrow(() -> new NotFoundException("Изображение с ид. " + imageId + " не найдено"));
        return picture.getData();
    }
}
