package io.github.zero88.rql.jooq.criteria.comparison;

import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;

import io.github.zero88.rql.jooq.JooqArgumentParser;
import io.github.zero.rql.parser.ast.BuiltinComparisonOperator;
import io.github.zero88.rql.jooq.criteria.JooqComparisonCriteriaBuilder;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import lombok.NonNull;

//TODO implement it
public final class NotLikeBuilder extends JooqComparisonCriteriaBuilder {

    public static final ComparisonOperator OPERATOR = BuiltinComparisonOperator.NOT_LIKE;

    protected NotLikeBuilder(@NonNull ComparisonNode node) {
        super(node);
    }

    @Override
    protected @NonNull Condition compare(@NonNull Field field, @NonNull List<String> arguments,
                                         @NonNull JooqArgumentParser parser) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
