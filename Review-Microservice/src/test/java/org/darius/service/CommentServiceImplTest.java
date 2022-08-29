package org.darius.service;

import org.darius.dto.request.insert.CommentInsertDTO;
import org.darius.dto.response.CommentResponseDTO;
import org.darius.entity.Comment;
import org.darius.entity.Review;
import org.darius.exception.EntityNotFoundException;
import org.darius.mapper.CommentMapper;
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

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("The add comment method should insert the entity")
    void addCommentShouldInsert() throws EntityNotFoundException {
        Long reviewId = 1L;
        CommentInsertDTO commentInsertDTO = new CommentInsertDTO();
        commentInsertDTO.setContent("content");
        commentInsertDTO.setReviewId(reviewId);

        Comment comment = CommentMapper.commentInsertDTOToComment(commentInsertDTO);

        Review review = new Review();
        review.setId(reviewId);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(review));
        when(commentRepository.save(comment)).thenReturn(comment);

        CommentResponseDTO commentResponseDTO = commentService.addComment(commentInsertDTO);
        assertEquals(commentResponseDTO.getContent(), comment.getContent());

        verify(reviewRepository).findById(reviewId);
    }

    @Test
    @DisplayName("The add comment method should throw EntityNotFoundException when review does not exist")
    void addCommentShouldThrowEntityNotFoundExceptionWhenReviewDoesNotExist() throws EntityNotFoundException {
        Long reviewId = 1L;
        CommentInsertDTO commentInsertDTO = new CommentInsertDTO();
        commentInsertDTO.setContent("content");
        commentInsertDTO.setReviewId(reviewId);

        Comment comment = CommentMapper.commentInsertDTOToComment(commentInsertDTO);

        Review review = new Review();
        review.setId(reviewId);

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.empty());
        when(commentRepository.save(comment)).thenReturn(comment);

        assertThrows(EntityNotFoundException.class, () -> commentService.addComment(commentInsertDTO));

        verify(reviewRepository).findById(reviewId);
    }

}