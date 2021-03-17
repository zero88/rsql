package io.zero88.rsql.criteria;

import cz.jirutka.rsql.parser.ast.Node;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public abstract class AbstractCriteriaBuilder<T extends Node> implements CriteriaBuilder<T> {

    @NonNull
    private final T node;

}
