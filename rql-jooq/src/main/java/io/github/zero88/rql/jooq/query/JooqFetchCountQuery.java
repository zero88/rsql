package io.github.zero88.rql.jooq.query;

import org.jooq.Condition;
import org.jooq.Query;

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

    @Override
    public @NonNull Query toQuery(@NonNull Condition condition) {
        return dsl().selectOne().from(table()).where(condition);
    }

}