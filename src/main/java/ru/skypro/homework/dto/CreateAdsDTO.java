package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.model.Ad;

@Data
public class CreateAdsDTO {
    private String description;
    private Integer price;
    private String title;

    public Ad toAd() {
        Ad ad = new Ad();
        ad.setDescription(description);
        ad.setTitle(title);
        ad.setPrice(price);
        return ad;
    }
}
