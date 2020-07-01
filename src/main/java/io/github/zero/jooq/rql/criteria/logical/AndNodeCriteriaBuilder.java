package io.github.zero.jooq.rql.criteria.logical;

import java.util.function.BiFunction;

import org.jooq.Condition;

import io.github.zero.jooq.rql.criteria.AbstractCriteriaBuilder;

import cz.jirutka.rsql.parser.ast.AndNode;
import lombok.NonNull;

public final class AndNodeCriteriaBuilder extends AbstractCriteriaBuilder<AndNode>
    implements LogicalCriteriaBuilder<AndNode> {

    public AndNodeCriteriaBuilder(@NonNull AndNode node) {
        super(node);
    }

    @Override
    @NonNull
    public BiFunction<Condition, Condition, Condition> logical() {
        return Condition::and;
    }

}
