package io.github.zero88.rql.jooq.criteria.comparison;

import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;

import io.github.zero88.rql.jooq.ArgumentParser;
import io.github.zero.rql.parser.ast.BuiltinComparisonOperator;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import lombok.NonNull;

public final class BetweenBuilder extends AbstractComparisonCriteriaBuilder {

    public static final ComparisonOperator OPERATOR = BuiltinComparisonOperator.BETWEEN;

    public BetweenBuilder(@NonNull ComparisonNode node) {
        super(node);
    }

    @Override
    protected @NonNull Condition compare(@NonNull Field field, @NonNull List<String> arguments,
                                         @NonNull ArgumentParser parser) {
        if (arguments.size() < 2) {
            throw new IllegalArgumentException("Between criteria requires 2 arguments");
        }
        return field.between(parser.parse(field, arguments.get(0)), parser.parse(field, arguments.get(1)));
    }

}