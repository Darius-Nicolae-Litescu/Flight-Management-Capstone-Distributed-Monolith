package org.darius.mapper;

import org.darius.dto.request.insert.ReviewInsertDTO;
import org.darius.dto.response.ReviewResponseDTO;
import org.darius.entity.Comment;
import org.darius.entity.Review;

import java.util.ArrayList;

public class ReviewMapper {
    public static Review reviewInsertDTOToReview(ReviewInsertDTO reviewInsertDTO) {
        if (reviewInsertDTO == null) {
            return null;
        }
        Review review = new Review();
        review.setContent(reviewInsertDTO.getContent());
        review.setNumberOfStars(reviewInsertDTO.getNumberOfStars());
        review.setFlightId(reviewInsertDTO.getFlightId());
        return review;
    }

    public static ReviewResponseDTO reviewToReviewResponseDTO(Review review) {
        if (review == null) {
            return null;
        }
        ReviewResponseDTO reviewResponseDTO = new ReviewResponseDTO();
        reviewResponseDTO.setContent(review.getContent());
        reviewResponseDTO.setNumberOfStars(review.getNumberOfStars());
        reviewResponseDTO.setFlightId(review.getFlightId());
        reviewResponseDTO.setId(review.getId());
        reviewResponseDTO.setComments(new ArrayList<>());
        for (Comment comment : review.getComments()) {
            reviewResponseDTO.getComments().add(CommentMapper.commentToCommentResponseDTO(comment, review.getId()));
        }
        return reviewResponseDTO;
    }
}
