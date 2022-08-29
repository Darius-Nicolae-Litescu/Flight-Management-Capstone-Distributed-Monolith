package org.darius.controller.comment;

import org.darius.dto.request.insert.CommentInsertDTO;
import org.darius.dto.request.insert.ReviewInsertDTO;
import org.darius.dto.response.CommentResponseDTO;
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
public class CommentUserController {
    private final  Logger logger = LoggerFactory.getLogger(CommentUserController.class);
    private final CommentService commentService;

    @Autowired
    public CommentUserController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/comment")
    public ResponseEntity<CommentResponseDTO> addComment(@RequestBody @Valid CommentInsertDTO commentInsertDTO) throws EntityNotFoundException {
        logger.info("Trying to add comment: {}", commentInsertDTO);
        CommentResponseDTO commentResponseDTO = commentService.addComment(commentInsertDTO);
        if (commentResponseDTO == null) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(commentResponseDTO, HttpStatus.CREATED);
    }
}
