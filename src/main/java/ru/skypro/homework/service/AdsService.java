package ru.skypro.homework.service;

import lombok.AllArgsConstructor;
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

    public AdsDTO postAd(CreateAdsDTO createAdsDTO, MultipartFile imageFile,
                         Long userId) {
        Ad ad = createAdsDTO.toAd();
        ad.setAuthor(usersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ид. " +
                        userId + " не найден")));

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
        AdPicture savedPicture = adPictureRepository.save(picture);

        return "/picture/" + savedPicture.getId().toString();
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

    public ResponseWrapperAdsDTO getAuthorisedUserAds(Long userId) {
        User user = usersRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с ид. " + userId + " не найден"));
        List<Ad> ads = adsRepository.findByAuthorOrderByPublishDateTimeDesc(user);
        List<AdsDTO> adsDTOList = ads.stream()
                .map(AdsDTO::fromAd)
                .collect(Collectors.toList());
        ResponseWrapperAdsDTO responseDTO = new ResponseWrapperAdsDTO();
        responseDTO.setResults(adsDTOList);
        responseDTO.setCount(adsDTOList.size());
        return responseDTO;
    }

    public byte[] updateAdImage(Long id, MultipartFile image) {
        Ad ad = adsRepository.findById(id).orElseThrow(() -> new NotFoundException("Объявление с идентификатором " +
                id + " не найдено"));

        String pictureId = ad.getImage();
        AdPicture picture;
        if (pictureId == null) {
            picture = null;
        } else {
            picture = adPictureRepository.findById(Long.parseLong(pictureId.replace("/picture/", "")))
                    .orElse(null);
        }
        if (picture == null) {
            String newPictureId = savePicture(image);
            picture = adPictureRepository.getReferenceById(Long.parseLong(newPictureId.replace("/picture/", "")));
            ad.setImage(newPictureId);
            adsRepository.save(ad);
        }
        try {
            picture.setData(image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return adPictureRepository.save(picture).getData();
    }
}
