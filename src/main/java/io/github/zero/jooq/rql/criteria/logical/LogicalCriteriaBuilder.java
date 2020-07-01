package io.github.zero.jooq.rql.criteria.logical;

import java.util.function.BiFunction;

import org.jooq.Condition;
import org.jooq.TableLike;
import org.jooq.impl.DSL;

import io.github.zero.jooq.rql.QueryContext;
import io.github.zero.jooq.rql.criteria.CriteriaBuilder;
import io.github.zero.jooq.rql.criteria.CriteriaBuilderFactory;

import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.NonNull;

public interface LogicalCriteriaBuilder<T extends LogicalNode> extends CriteriaBuilder<T> {

    @Override
    default @NonNull Condition build(@NonNull TableLike table, @NonNull QueryContext queryContext,
                                     @NonNull CriteriaBuilderFactory factory) {
        final Condition[] condition = new Condition[] {DSL.trueCondition()};
        boolean isFirst = true;
        for (Node node : node()) {
            if (isFirst) {
                condition[0] = condition[0].and(each(table, queryContext, factory, node));
            } else {
                condition[0] = logical().apply(condition[0], each(table, queryContext, factory, node));
            }
            isFirst = false;
        }
        return condition[0];
    }

    @NonNull
    default Condition each(@NonNull TableLike table, @NonNull QueryContext queryContext,
                           @NonNull CriteriaBuilderFactory factory, @NonNull Node subNode) {
        return factory.create(subNode).build(table, queryContext, factory);
    }

    @NonNull BiFunction<Condition, Condition, Condition> logical();

}
