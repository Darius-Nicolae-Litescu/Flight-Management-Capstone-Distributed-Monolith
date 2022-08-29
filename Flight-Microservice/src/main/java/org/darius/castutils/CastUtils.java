package org.darius.castutils;

import java.util.List;
import java.util.stream.Collectors;

public class CastUtils<T> {
    public <T> List<T> castList(List<?> list, Class<T> clazz) {
        List<T> castedList = list.stream().map(item -> {
            if (clazz.isInstance(item)) {
                return clazz.cast(item);
            } else {
                throw new ClassCastException("Cannot cast " + item.getClass() + " to " + clazz);
            }
        }).collect(Collectors.toList());
        return castedList;
    }
}
