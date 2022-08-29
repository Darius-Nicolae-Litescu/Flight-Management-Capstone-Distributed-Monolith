package org.darius.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightOperationWrapper <T> implements Serializable {
    private String message;
    private T result;
}
