package org.darius.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.darius.dto.request.insert.CommentInsertDTO;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewResponseDTO implements Serializable {
    private Long id;
    private String content;
    private Long flightId;
    private Integer numberOfStars;
    private List<CommentResponseDTO> comments;
}
