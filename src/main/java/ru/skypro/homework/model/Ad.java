package ru.skypro.homework.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
@Table(name = "ads")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
    @Column(nullable = false)
    private String title;
    private String description;
    @Column(nullable = false)
    private Integer price;
    private String image;
    @Column(name = "publish_date_time")
    private Instant publishDateTime;
}
