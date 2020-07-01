package io.github.zero.jooq.rql.criteria;

import io.github.zero.jooq.rql.criteria.comparision.ComparisonCriteriaBuilder;
import io.github.zero.jooq.rql.criteria.logical.AndNodeCriteriaBuilder;
import io.github.zero.jooq.rql.criteria.logical.OrNodeCriteriaBuilder;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.OrNode;
import lombok.NonNull;

/**
 * The interface Criteria builder factory.
 *
 * @since 1.0.0
 */
public interface CriteriaBuilderFactory {

    /**
     * The constant DEFAULT.
     */
    CriteriaBuilderFactory DEFAULT = new CriteriaBuilderFactory() {};

    /**
     * Create criteria builder.
     *
     * @param <T>  Type of {@code Node}
     * @param node the node
     * @return the criteria builder
     * @see Node
     * @since 1.0.0
     */
    @SuppressWarnings("unchecked")
    default <T extends Node> CriteriaBuilder<T> create(@NonNull T node) {
        if (node instanceof AndNode) {
            return (CriteriaBuilder<T>) new AndNodeCriteriaBuilder((AndNode) node);
        }
        if (node instanceof OrNode) {
            return (CriteriaBuilder<T>) new OrNodeCriteriaBuilder((OrNode) node);
        }
        if (node instanceof ComparisonNode) {
            return (CriteriaBuilder<T>) ComparisonCriteriaBuilder.create((ComparisonNode) node);
        }
        throw new IllegalArgumentException("Unknown node type " + node.getClass().getSimpleName());
    }

}
