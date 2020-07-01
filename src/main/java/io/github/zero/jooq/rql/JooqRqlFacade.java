package io.github.zero.jooq.rql;

import io.github.zero.jooq.rql.criteria.CriteriaBuilderFactory;

import lombok.NonNull;

/**
 * The interface jOOQ RQL facade that wraps some condiments to enhance parsing SQL query in runtime
 *
 * @since 1.0.0
 */
public interface JooqRqlFacade {

    /**
     * Gets Query context.
     *
     * @return the query context
     * @see QueryContext
     * @since 1.0.0
     */
    default @NonNull QueryContext queryContext() {
        return QueryContext.DEFAULT;
    }

    /**
     * Criteria builder factory criteria builder factory.
     *
     * @return the criteria builder factory
     * @see CriteriaBuilderFactory
     * @since 1.0.0
     */
    default @NonNull CriteriaBuilderFactory criteriaBuilderFactory() {
        return CriteriaBuilderFactory.DEFAULT;
    }

}
