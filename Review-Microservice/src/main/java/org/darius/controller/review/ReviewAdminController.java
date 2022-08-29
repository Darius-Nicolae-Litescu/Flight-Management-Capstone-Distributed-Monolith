package org.darius.controller.review;

import org.darius.dto.response.ReviewResponseDTO;
import org.darius.exception.EntityNotFoundException;
import org.darius.service.CommentService;
import org.darius.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/review/admin")
public class ReviewAdminController {
    private final Logger logger = LoggerFactory.getLogger(ReviewAdminController.class);
    private final ReviewService reviewService;
    private final CommentService commentService;

    @Autowired
    public ReviewAdminController(ReviewService reviewService, CommentService commentService) {
        this.reviewService = reviewService;
        this.commentService = commentService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReviewResponseDTO> deleteReview(@PathVariable Long id) throws EntityNotFoundException {
        logger.info("Trying to delete review with id: {}", id);
        ReviewResponseDTO reviewResponseDTO = reviewService.deleteReview(id);
        if (reviewResponseDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(reviewResponseDTO, HttpStatus.OK);
    }

}
