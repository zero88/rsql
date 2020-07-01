package io.github.zero.jooq.rql.criteria;

import org.jooq.Condition;
import org.jooq.Table;
import org.jooq.TableLike;

import io.github.zero.jooq.rql.QueryContext;

import cz.jirutka.rsql.parser.ast.Node;
import lombok.NonNull;

/**
 * The interface Criteria builder.
 *
 * @param <T> Type of {@code Node}
 * @see Node
 * @since 1.0.0
 */
public interface CriteriaBuilder<T extends Node> {

    /**
     * Defines Node.
     *
     * @return the node
     * @since 1.0.0
     */
    @NonNull T node();

    /**
     * Build condition.
     *
     * @param table the table
     * @return the condition
     * @apiNote It is equivalent to call {@link #build(TableLike, QueryContext, CriteriaBuilderFactory)} with {@link
     *     QueryContext#DEFAULT} and {@link CriteriaBuilderFactory#DEFAULT}
     * @since 1.0.0
     */
    default @NonNull Condition build(@NonNull TableLike table) {
        return build(table, QueryContext.DEFAULT, CriteriaBuilderFactory.DEFAULT);
    }

    /**
     * Build condition.
     *
     * @param table        the table
     * @param queryContext the query context
     * @param factory      the criteria builder factory
     * @return the condition
     * @see Condition
     * @see Table
     * @see QueryContext
     * @see CriteriaBuilderFactory
     * @since 1.0.0
     */
    @NonNull Condition build(@NonNull TableLike table, @NonNull QueryContext queryContext,
                             @NonNull CriteriaBuilderFactory factory);

}
