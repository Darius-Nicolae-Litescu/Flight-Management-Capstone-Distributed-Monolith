package org.darius.controller.comment;

import org.darius.dto.response.CommentResponseDTO;
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
public class CommentAdminController {
    private final  Logger logger = LoggerFactory.getLogger(CommentAdminController.class);
    private final CommentService commentService;

    @Autowired
    public CommentAdminController(CommentService commentService) {
        this.commentService = commentService;
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<CommentResponseDTO> deleteComment(@PathVariable Long id) throws EntityNotFoundException {
        logger.info("Trying to delete comment with id: {}", id);
        CommentResponseDTO commentResponseDTO = commentService.deleteComment(id);
        if (commentResponseDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(commentResponseDTO, HttpStatus.OK);
    }

}
