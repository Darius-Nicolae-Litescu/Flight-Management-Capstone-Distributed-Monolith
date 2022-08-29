package org.darius.service;

import org.darius.dto.request.insert.CommentInsertDTO;
import org.darius.dto.request.insert.ReviewInsertDTO;
import org.darius.dto.response.CommentResponseDTO;
import org.darius.dto.response.ReviewResponseDTO;
import org.darius.entity.Comment;
import org.darius.entity.Review;
import org.darius.exception.EntityNotFoundException;
import org.darius.mapper.CommentMapper;
import org.darius.mapper.ReviewMapper;
import org.darius.repository.CommentRepository;
import org.darius.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private FlightService flightService;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("The add review method should insert the entity")
    void addReviewShouldInsert() throws EntityNotFoundException {
        Long flightId = 1L;
        
        ReviewInsertDTO reviewInsertDTO = new ReviewInsertDTO();
        reviewInsertDTO.setFlightId(flightId);
        reviewInsertDTO.setContent("content");

        Review review = ReviewMapper.reviewInsertDTOToReview(reviewInsertDTO);


        when(flightService.checkIfFlightExists(flightId)).thenReturn(Boolean.TRUE);
        when(reviewRepository.save(review)).thenReturn(review);

        ReviewResponseDTO reviewResponseDTO = reviewService.addReview(reviewInsertDTO);

        assertEquals(reviewResponseDTO.getContent(), review.getContent());

        verify(reviewRepository).save(review);
    }

    @Test
    @DisplayName("The add review method should throw EntityNotFoundException when flight does not exist")
    void addReviewShouldThrowExceptionWhenFlightDoesNotExist() throws EntityNotFoundException {
        Long flightId = 1L;

        ReviewInsertDTO reviewInsertDTO = new ReviewInsertDTO();
        reviewInsertDTO.setFlightId(flightId);
        reviewInsertDTO.setContent("content");

        Review review = ReviewMapper.reviewInsertDTOToReview(reviewInsertDTO);

        when(flightService.checkIfFlightExists(flightId)).thenReturn(Boolean.FALSE);
        assertThrows(EntityNotFoundException.class, () -> reviewService.addReview(reviewInsertDTO));

        verify(flightService).checkIfFlightExists(flightId);
    }

    @Test
    @DisplayName("The getReviewById method should return the entity")
    void getReviewByIdShouldReturn() throws EntityNotFoundException {
        Long reviewId = 1L;

        Review review = new Review();
        review.setId(reviewId);
        review.setContent("content");
        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));

        ReviewResponseDTO reviewResponseDTO = reviewService.getReviewById(reviewId);
        assertEquals(reviewResponseDTO.getContent(), review.getContent());

        verify(reviewRepository).findById(reviewId);
    }

    @Test
    @DisplayName("The getReviewById method should throw EntityNotFoundException when review does not exist")
    void getReviewByIdShouldThrowExceptionWhenReviewDoesNotExist() throws EntityNotFoundException {
        Long reviewId = 1L;

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> reviewService.getReviewById(reviewId));

        verify(reviewRepository).findById(reviewId);
    }

    @Test
    @DisplayName("The getReviewsByFlightId method should return the entities")
    void getReviewByFlightIdShouldReturn() throws EntityNotFoundException {
        Long flightId = 1L;

        List<Review> reviews = new ArrayList<>();
        Review review = new Review();
        review.setId(flightId);
        review.setContent("content");
        reviews.add(review);

        List<ReviewResponseDTO> resultDTOs = reviews.stream()
                .map(ReviewMapper::reviewToReviewResponseDTO).collect(Collectors.toList());

        when(reviewRepository.findByFlightId(flightId)).thenReturn(reviews);
        when(flightService.checkIfFlightExists(flightId)).thenReturn(Boolean.TRUE);

        List<ReviewResponseDTO> reviewResponseDTOs = reviewService.getReviewsByFlightId(flightId);

        assertEquals(resultDTOs, reviewResponseDTOs);

        verify(reviewRepository).findByFlightId(flightId);
    }

    @Test
    @DisplayName("The getReviewsByFlightId method should throw EntityNotFoundException when flight does not exist")
    void getReviewByFlightIdShouldThrowExceptionWhenFlightDoesNotExist() throws EntityNotFoundException {
        Long flightId = 1L;

        when(flightService.checkIfFlightExists(flightId)).thenReturn(Boolean.FALSE);
        assertThrows(EntityNotFoundException.class, () -> reviewService.getReviewsByFlightId(flightId));

        verify(flightService).checkIfFlightExists(flightId);
    }

}