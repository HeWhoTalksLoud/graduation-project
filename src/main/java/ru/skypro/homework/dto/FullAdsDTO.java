package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.model.Ad;

@Data
public class FullAdsDTO {
    private Long pk;
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email;
    private String image;
    private String phone;
    private Integer price;
    private String title;

    public static FullAdsDTO fromAd(Ad ad) {
        FullAdsDTO dto = new FullAdsDTO();
        dto.setPk(ad.getId());
        dto.setAuthorFirstName(ad.getAuthor().getFirstName());
        dto.setAuthorLastName(ad.getAuthor().getLastName());
        dto.setDescription(ad.getDescription());
        dto.setEmail(ad.getAuthor().getEmail());
        dto.setImage(ad.getImage());
        dto.setPhone(ad.getAuthor().getPhone());
        dto.setPrice(ad.getPrice());
        dto.setTitle(ad.getTitle());
        return dto;
    }
}
