package org.darius.dto.request.insert;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.darius.validators.WordsNotAllowedConstraint;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentInsertDTO implements Serializable {
    @NotNull(message = "Content is required")
    @Size(min = 1, message = "Content is required")
    @WordsNotAllowedConstraint
    private String content;

    @NotNull(message = "Flight id is required")
    @Min(1)
    private Long reviewId;

}
