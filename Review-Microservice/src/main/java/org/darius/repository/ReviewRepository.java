package org.darius.repository;

import org.darius.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    @Query("select r from Review r where r.flightId = ?1")
    List<Review> findByFlightId(Long id);

    @Query("select r from Review r join r.comments c where c.id = ?1")
    Optional<Review> getReviewByCommentId(Long id);
}
