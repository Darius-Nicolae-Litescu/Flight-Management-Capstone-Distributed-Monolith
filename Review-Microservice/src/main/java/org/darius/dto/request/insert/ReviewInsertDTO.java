package org.darius.dto.request.insert;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.darius.validators.WordsNotAllowedConstraint;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewInsertDTO implements Serializable {
    @NotNull(message = "Flight id is required")
    @Min(1)
    private Long flightId;

    @NotNull(message = "Content is required")
    @Size(min = 1, message = "Content should be at least 1 character long")
    @WordsNotAllowedConstraint
    private String content;

    @Range(min=0, max=5, message = "Number of stars must be between 0 and 5")
    private Integer numberOfStars;
}
