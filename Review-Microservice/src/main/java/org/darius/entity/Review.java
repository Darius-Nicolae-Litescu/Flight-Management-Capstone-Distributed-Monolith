package org.darius.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "flight_id")
    private Long flightId;

    @Column(name = "number_of_stars")
    private Integer numberOfStars;

    @Column(name = "content")
    private String content;

    @CreationTimestamp
    @Column(name = "posted_at")
    private LocalDateTime postedAt;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "review_id")
    private List<Comment> comments = new ArrayList<>();


}
