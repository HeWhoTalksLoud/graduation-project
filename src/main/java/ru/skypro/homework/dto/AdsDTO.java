package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.model.Ad;

@Data
public class AdsDTO {
    private Long author;
    private String image;
    private Long pk;
    private Integer price;
    private String title;

    public static AdsDTO fromAd(Ad ad) {
        AdsDTO dto = new AdsDTO();
        dto.setTitle(ad.getTitle());
        dto.setAuthor(ad.getAuthor().getId());
        dto.setPk(ad.getId());
        dto.setPrice(ad.getPrice());
        dto.setImage(ad.getImage());
        return dto;
    }
}
