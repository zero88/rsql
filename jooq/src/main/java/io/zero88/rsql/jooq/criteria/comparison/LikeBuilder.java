package io.zero88.rsql.jooq.criteria.comparison;

import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.impl.DSL;

import io.zero88.rsql.LikeWildcardPattern;
import io.zero88.rsql.jooq.JooqArgumentParser;
import io.zero88.rsql.jooq.criteria.JooqComparisonCriteriaBuilder;
import io.zero88.rsql.parser.ast.ComparisonOperatorProxy;

import lombok.NonNull;

//TODO implement it
public final class LikeBuilder extends JooqComparisonCriteriaBuilder {

    @Override
    public @NonNull ComparisonOperatorProxy operator() {
        return ComparisonOperatorProxy.LIKE;
    }

    @Override
    protected @NonNull Condition compare(@NonNull Field field, @NonNull List<String> arguments,
                                         @NonNull JooqArgumentParser argParser,
                                         @NonNull LikeWildcardPattern wildcardPattern) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
