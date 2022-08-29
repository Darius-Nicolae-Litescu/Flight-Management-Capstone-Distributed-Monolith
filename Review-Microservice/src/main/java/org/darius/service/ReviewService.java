package org.darius.service;

import org.darius.dto.request.insert.ReviewInsertDTO;
import org.darius.dto.response.ReviewResponseDTO;
import org.darius.entity.Review;
import org.darius.exception.EntityNotFoundException;

import java.util.List;

public interface ReviewService {
    ReviewResponseDTO addReview(ReviewInsertDTO reviewInsertDTO) throws EntityNotFoundException;

    ReviewResponseDTO deleteReview(Long id) throws EntityNotFoundException;

    ReviewResponseDTO getReviewById(Long id) throws EntityNotFoundException;

    List<ReviewResponseDTO> getReviewsByFlightId(Long id) throws EntityNotFoundException;
}
