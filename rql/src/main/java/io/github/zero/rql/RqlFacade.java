package io.github.zero.rql;

import io.github.zero.rql.criteria.CriteriaBuilder;
import io.github.zero.rql.criteria.CriteriaBuilderFactory;

import cz.jirutka.rsql.parser.ast.Node;
import lombok.NonNull;

public interface RqlFacade {

    /**
     * Gets Query context.
     *
     * @return the query context
     * @see QueryContext
     * @since 1.0.0
     */
    @NonNull QueryContext queryContext();

    /**
     * Criteria builder factory criteria builder factory.
     *
     * @return the criteria builder factory
     * @see CriteriaBuilderFactory
     * @since 1.0.0
     */
    @NonNull <T extends Node, C extends CriteriaBuilder<T>> CriteriaBuilderFactory<T, C> criteriaBuilderFactory();

}
