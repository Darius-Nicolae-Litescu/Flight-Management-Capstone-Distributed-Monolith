package org.darius.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface QuadFunction<A, B, C, D, R> {

    R apply(A a, B u, C v, D d);

    default <S> QuadFunction<A, B, C, D, S> andThen(
            Function<? super R, S> after) {
        return (A a, B b, C c, D d) -> after.apply(apply(a, b, c, d));
    }
}