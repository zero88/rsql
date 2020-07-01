package io.github.zero.jooq.rql.query;

import org.jooq.Condition;

import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * Represents for jOOQ fetch count query.
 *
 * @see JooqConditionQuery
 * @since 1.0.0
 */
@SuperBuilder
public final class JooqFetchCountQuery extends AbstractJooqConditionQuery<Integer> {

    @Override
    public Integer execute(@NonNull Condition condition) {
        return dsl().selectCount().from(table()).where(condition).fetch().get(0).value1();
    }

}