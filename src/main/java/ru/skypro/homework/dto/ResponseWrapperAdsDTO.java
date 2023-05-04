package ru.skypro.homework.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseWrapperAdsDTO {
    private Integer count;
    private List<AdsDTO> results;
}
