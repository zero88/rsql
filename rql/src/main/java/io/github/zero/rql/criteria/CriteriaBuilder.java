package io.github.zero.rql.criteria;

import cz.jirutka.rsql.parser.ast.Node;
import lombok.NonNull;

public interface CriteriaBuilder<T extends Node> {

    /**
     * Defines Node.
     *
     * @return the node
     * @since 1.0.0
     */
    @NonNull T node();

}
