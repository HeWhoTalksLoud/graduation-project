package ru.skypro.homework.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class AdPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] data;
}
