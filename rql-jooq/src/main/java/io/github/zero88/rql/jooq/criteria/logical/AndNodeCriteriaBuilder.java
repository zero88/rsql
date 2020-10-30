package io.github.zero88.rql.jooq.criteria.logical;

import java.util.function.BiFunction;

import org.jooq.Condition;

import io.github.zero.rql.criteria.AbstractCriteriaBuilder;
import io.github.zero88.rql.jooq.criteria.JooqLogicalCriteriaBuilder;

import cz.jirutka.rsql.parser.ast.AndNode;
import lombok.NonNull;

public final class AndNodeCriteriaBuilder extends AbstractCriteriaBuilder<AndNode>
    implements JooqLogicalCriteriaBuilder<AndNode> {

    public AndNodeCriteriaBuilder(@NonNull AndNode node) {
        super(node);
    }

    @Override
    @NonNull
    public BiFunction<Condition, Condition, Condition> logical() {
        return Condition::and;
    }

}
