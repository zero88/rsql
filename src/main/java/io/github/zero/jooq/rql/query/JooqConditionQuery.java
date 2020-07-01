package io.github.zero.jooq.rql.query;

import org.jooq.Condition;
import org.jooq.TableLike;

import io.github.zero.jooq.rql.JooqRqlQuery;

import lombok.NonNull;

/**
 * The interface Jooq condition query.
 *
 * @param <R> Type of {@code Result}
 * @see Condition
 * @see JooqRqlQuery
 * @since 1.0.0
 */
public interface JooqConditionQuery<R> extends JooqRqlQuery<R, Condition, Void> {

    /**
     * Defines table.
     *
     * @return the table
     * @see TableLike
     * @since 1.0.0
     */
    @NonNull TableLike table();

    /**
     * Execute query by condition.
     *
     * @param condition the condition
     * @return the result
     * @since 1.0.0
     */
    @NonNull R execute(@NonNull Condition condition);

}
