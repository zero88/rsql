package io.github.zero88.rql.jooq.criteria.comparison;

import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;

import io.github.zero88.rql.jooq.ArgumentParser;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.RSQLOperators;
import lombok.NonNull;

public final class EqualBuilder extends AbstractComparisonCriteriaBuilder {

    public static final ComparisonOperator OPERATOR = RSQLOperators.EQUAL;

    public EqualBuilder(@NonNull ComparisonNode node) {
        super(node);
    }

    protected @NonNull Condition compare(@NonNull Field field, @NonNull List<String> arguments,
                                         @NonNull ArgumentParser parser) {
        return field.eq(parser.parse(field, arguments.get(0)));
    }

}
