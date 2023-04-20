package ru.skypro.homework.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateAdsDTO;
import ru.skypro.homework.dto.FullAdsDTO;
import ru.skypro.homework.dto.ResponseWrapperAdsDTO;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.AdPicture;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdPictureRepository;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.UsersRepository;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdsService {
    private final AdsRepository adsRepository;
    private final UsersRepository usersRepository;
    private final AdPictureRepository adPictureRepository;

    public ResponseWrapperAdsDTO getAllAds() {
        List<Ad> ads = adsRepository.findAllByOrderByPublishDateTimeDesc();
        if (ads.isEmpty()) {
            throw new NotFoundException("Объявления отсутствуют");
        }
        List<AdsDTO> adsDTOList = ads.stream()
                .map(AdsDTO::fromAd)
                .collect(Collectors.toList());
        ResponseWrapperAdsDTO responseDTO = new ResponseWrapperAdsDTO();
        responseDTO.setResults(adsDTOList);
        responseDTO.setCount(adsDTOList.size());
        return responseDTO;
    }

    public AdsDTO postAd(CreateAdsDTO createAdsDTO, MultipartFile imageFile) {
        Ad ad = createAdsDTO.toAd();
        // ad.setId(null);
        // todo Исправить
        ad.setAuthor(usersRepository.findById(1L).orElse(new User()));

        ad.setImage(savePicture(imageFile));
        ad.setPublishDateTime(Instant.now());

        Ad savedAd = adsRepository.save(ad);
        return AdsDTO.fromAd(savedAd);
    }

    private String savePicture(MultipartFile imageFile) {
        AdPicture picture = new AdPicture();
        try {
            byte[] bytes = imageFile.getBytes();
            picture.setData(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String id = "/picture/" + RandomStringUtils.randomAlphabetic(4);
        picture.setId(id);
        AdPicture savedPicture = adPictureRepository.save(picture);

        return savedPicture.getId();
    }

    public  FullAdsDTO getAdInfo(Long adId) {
        Ad ad = adsRepository.findById(adId).orElseThrow(() -> new NotFoundException("Объявление с идентификатором " +
                adId + " не найдено"));
        return FullAdsDTO.fromAd(ad);
    }

    public void deleteAd(Long adId) {
        adsRepository.deleteById(adId);
    }

    public AdsDTO updateAd(Long adId, CreateAdsDTO createAdsDTO) {
        Ad ad = adsRepository.findById(adId).orElseThrow(() -> new NotFoundException("Объявление с идентификатором " +
                adId + " не найдено"));
        ad.setDescription(createAdsDTO.getDescription());
        ad.setPrice(createAdsDTO.getPrice());
        ad.setTitle(createAdsDTO.getTitle());
        Ad savedAd = adsRepository.save(ad);
        return AdsDTO.fromAd(savedAd);
    }

    // todo исправить
    public ResponseWrapperAdsDTO getAuthorisedUserAds() {
        User user = usersRepository.findById(1L).orElseThrow(() -> new NotFoundException("getAuthorisedUserAds()"));
        List<Ad> ads = adsRepository.findByAuthor(user);
        List<AdsDTO> adsDTOList = ads.stream()
                .map(AdsDTO::fromAd)
                .collect(Collectors.toList());
        ResponseWrapperAdsDTO responseDTO = new ResponseWrapperAdsDTO();
        responseDTO.setResults(adsDTOList);
        responseDTO.setCount(adsDTOList.size());
        return responseDTO;
    }
}