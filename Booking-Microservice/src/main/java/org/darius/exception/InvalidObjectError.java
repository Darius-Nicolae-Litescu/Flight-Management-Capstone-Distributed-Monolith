package org.darius.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@AllArgsConstructor
public class InvalidObjectError implements Serializable {
    private Map<String, String> fieldsWithErrors;
}
