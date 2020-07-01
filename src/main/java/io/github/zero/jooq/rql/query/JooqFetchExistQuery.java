package io.github.zero.jooq.rql.query;

import org.jooq.Condition;
import org.jooq.impl.DSL;

import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * Represents for jOOQ fetch exist query.
 *
 * @see JooqConditionQuery
 * @since 1.0.0
 */
@SuperBuilder
public final class JooqFetchExistQuery extends AbstractJooqConditionQuery<Boolean> {

    @Override
    public Boolean execute(@NonNull Condition condition) {
        return dsl().fetchExists(dsl().select(DSL.asterisk()).from(table()).where(condition));
    }

}
