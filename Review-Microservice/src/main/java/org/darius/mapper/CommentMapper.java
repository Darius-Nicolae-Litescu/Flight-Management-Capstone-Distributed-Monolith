package org.darius.mapper;

import org.darius.dto.request.insert.CommentInsertDTO;
import org.darius.dto.response.CommentResponseDTO;
import org.darius.entity.Comment;

import java.time.format.DateTimeFormatter;

public class CommentMapper {

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static Comment commentInsertDTOToComment(CommentInsertDTO commentInsertDTO) {
        if (commentInsertDTO == null) {
            return null;
        }
        Comment comment = new Comment();
        comment.setContent(commentInsertDTO.getContent());
        return comment;
    }

    public static CommentResponseDTO commentToCommentResponseDTO(Comment comment, Long reviewId) {
        if (comment == null) {
            return null;
        }
        CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
        commentResponseDTO.setId(comment.getId());
        commentResponseDTO.setReviewId(reviewId);
        commentResponseDTO.setPostedAt(formatter.format(comment.getPostedAt()));
        commentResponseDTO.setContent(comment.getContent());
        return commentResponseDTO;
    }


}
