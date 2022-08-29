package org.darius.controller.review;

import org.darius.dto.request.insert.ReviewInsertDTO;
import org.darius.dto.response.ReviewResponseDTO;
import org.darius.exception.EntityNotFoundException;
import org.darius.service.CommentService;
import org.darius.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/review/user")
public class ReviewUserController {
    private final Logger logger = LoggerFactory.getLogger(ReviewUserController.class);
    private final ReviewService reviewService;
    private final CommentService commentService;

    @Autowired
    public ReviewUserController(ReviewService reviewService, CommentService commentService) {
        this.reviewService = reviewService;
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<ReviewResponseDTO> addReview(@RequestBody @Valid ReviewInsertDTO reviewInsertDTO) throws EntityNotFoundException {
        logger.info("Trying to add review: {}", reviewInsertDTO.getNumberOfStars());
        ReviewResponseDTO reviewResponseDTO = reviewService.addReview(reviewInsertDTO);
        if (reviewResponseDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(reviewResponseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/flight/{flightId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewByFlightId(@PathVariable("flightId") Long flightId) throws EntityNotFoundException {
        logger.info("Trying to get review by flight id: {}", flightId);
        List<ReviewResponseDTO> reviewResponseDTOs = reviewService.getReviewsByFlightId(flightId);
        if (reviewResponseDTOs == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(reviewResponseDTOs, HttpStatus.OK);
    }

    @GetMapping("/id/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> getReviewById(@PathVariable("reviewId") Long reviewId) throws EntityNotFoundException {
        logger.info("Trying to get review by id: {}", reviewId);
        ReviewResponseDTO reviewResponseDTO = reviewService.getReviewById(reviewId);
        if (reviewResponseDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(reviewResponseDTO, HttpStatus.OK);
    }

}
