package io.zero88.rsql.jooq.criteria.comparison;

import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;

import io.zero88.rsql.jooq.JooqArgumentParser;
import io.zero88.rsql.jooq.criteria.JooqComparisonCriteriaBuilder;
import io.zero88.rsql.parser.ast.ComparisonOperatorProxy;

import lombok.NonNull;

public final class GreaterThanBuilder extends JooqComparisonCriteriaBuilder {

    @Override
    public @NonNull ComparisonOperatorProxy operator() {
        return ComparisonOperatorProxy.GREATER_THAN;
    }

    @Override
    protected @NonNull Condition compare(@NonNull Field field, @NonNull List<String> arguments,
                                         @NonNull JooqArgumentParser parser) {
        return field.gt(parser.parse(field, arguments.get(0)));
    }

}
