package org.darius.service;

import org.darius.dto.request.insert.CommentInsertDTO;
import org.darius.dto.response.CommentResponseDTO;
import org.darius.exception.EntityNotFoundException;

public interface CommentService {
    CommentResponseDTO addComment(CommentInsertDTO commentInsertDTO) throws EntityNotFoundException;
    CommentResponseDTO deleteComment(Long id) throws EntityNotFoundException;
}
