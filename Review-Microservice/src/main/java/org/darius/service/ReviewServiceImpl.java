package org.darius.service;

import org.darius.dto.request.insert.ReviewInsertDTO;
import org.darius.dto.response.ReviewResponseDTO;
import org.darius.entity.Review;
import org.darius.exception.EntityNotFoundException;
import org.darius.mapper.ReviewMapper;
import org.darius.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewRepository reviewRepository;
    private final FlightService flightService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, FlightService flightService) {
        this.reviewRepository = reviewRepository;
        this.flightService = flightService;
    }

    @Override
    public ReviewResponseDTO addReview(ReviewInsertDTO reviewInsertDTO) throws EntityNotFoundException {
        logger.info("addReview: {}", reviewInsertDTO);
        Boolean flightExists = flightService.checkIfFlightExists(reviewInsertDTO.getFlightId());
        if (!flightExists) {
            throw new EntityNotFoundException(reviewInsertDTO.getFlightId());
        }
        Review review = ReviewMapper.reviewInsertDTOToReview(reviewInsertDTO);
        reviewRepository.save(review);
        return ReviewMapper.reviewToReviewResponseDTO(review);
    }

    @Override
    public ReviewResponseDTO deleteReview(Long id) throws EntityNotFoundException {
        logger.info("deleteReview: {}", id);
        Optional<Review> review = reviewRepository.findById(id);
        if (!review.isPresent()) {
            throw new EntityNotFoundException(null, id, Review.class.getName());
        }
        reviewRepository.delete(review.get());
        logger.info("deleted review: {}", review);
        return ReviewMapper.reviewToReviewResponseDTO(review.get());
    }

    @Override
    public ReviewResponseDTO getReviewById(Long id) throws EntityNotFoundException {
        logger.info("getReviewById: {}", id);
        Optional<Review> review = reviewRepository.findById(id);
        if (!review.isPresent()) {
            throw new EntityNotFoundException(null, id, Review.class.getName());
        }
        return ReviewMapper.reviewToReviewResponseDTO(review.get());
    }

    @Override
    public List<ReviewResponseDTO> getReviewsByFlightId(Long id) throws EntityNotFoundException {
        logger.info("getReviewByFlightId: {}", id);
        Boolean flightExists = flightService.checkIfFlightExists(id);
        if (!flightExists) {
            throw new EntityNotFoundException(id);
        }
        List<Review> reviewList = reviewRepository.findByFlightId(id);
        if (reviewList.isEmpty()) {
            return new ArrayList<>();
        }
        return reviewList.stream().map(review -> ReviewMapper.reviewToReviewResponseDTO(review)).collect(Collectors.toList());
    }

}
