package io.github.zero.jooq.rql.criteria.comparision;

import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;

import io.github.zero.jooq.rql.ArgumentParser;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import lombok.NonNull;

public final class BetweenBuilder extends AbstractComparisionCriteriaBuilder {

    public static final ComparisonOperator OPERATOR = new ComparisonOperator("=between=", true);

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
