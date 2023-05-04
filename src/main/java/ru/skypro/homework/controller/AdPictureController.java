package ru.skypro.homework.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.service.AdPictureService;

@RestController
@RequestMapping("/picture")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class AdPictureController {

    private final AdPictureService service;

    @GetMapping(value = "/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getPicture(@PathVariable String id) {
        return service.getImageAsBytes(Long.parseLong(id));
    }
}
