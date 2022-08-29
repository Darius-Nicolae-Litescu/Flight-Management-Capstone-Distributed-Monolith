package org.darius.service;
import org.darius.dto.request.insert.CommentInsertDTO;
import org.darius.dto.response.CommentResponseDTO;
import org.darius.entity.Comment;
import org.darius.entity.Review;
import org.darius.exception.EntityNotFoundException;
import org.darius.mapper.CommentMapper;
import org.darius.repository.CommentRepository;
import org.darius.repository.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final  Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ReviewRepository reviewRepository) {
        this.commentRepository = commentRepository;
        this.reviewRepository = reviewRepository;
    }
    @Override
    @Transactional
    public CommentResponseDTO addComment(CommentInsertDTO commentInsertDTO) throws EntityNotFoundException {
        logger.info("addComment: {}", commentInsertDTO);
        Comment comment = CommentMapper.commentInsertDTOToComment(commentInsertDTO);
        Optional<Review> review = reviewRepository.findById(commentInsertDTO.getReviewId());
        if (!review.isPresent()) {
            throw new EntityNotFoundException(null, commentInsertDTO.getReviewId(), Review.class.getName());
        }
        comment.setPostedAt(LocalDateTime.now());
        commentRepository.save(comment);
        review.get().getComments().add(comment);
        reviewRepository.save(review.get());
        logger.info("added comment: {}", comment);
        return CommentMapper.commentToCommentResponseDTO(comment, review.get().getId());
    }

    @Override
    public CommentResponseDTO deleteComment(Long id) throws EntityNotFoundException {
        logger.info("deleteComment: {}", id);
        Optional<Comment> comment = commentRepository.findById(id);
        if (!comment.isPresent()) {
            throw new EntityNotFoundException(null, id, Comment.class.getName());
        }
        Optional<Review> review = reviewRepository.getReviewByCommentId(id);
        if (!review.isPresent()) {
            throw new EntityNotFoundException(null, id, Review.class.getName());
        }
        commentRepository.delete(comment.get());
        logger.info("deleted comment: {}", comment);
        return CommentMapper.commentToCommentResponseDTO(comment.get(), review.get().getId());
    }
}
