package io.github.zero88.rql.jooq.criteria.logical;

import java.util.function.BiFunction;

import org.jooq.Condition;

import io.github.zero.rql.criteria.AbstractCriteriaBuilder;
import io.github.zero88.rql.jooq.criteria.JooqLogicalCriteriaBuilder;

import cz.jirutka.rsql.parser.ast.OrNode;
import lombok.NonNull;

public final class OrNodeCriteriaBuilder extends AbstractCriteriaBuilder<OrNode>
    implements JooqLogicalCriteriaBuilder<OrNode> {

    public OrNodeCriteriaBuilder(@NonNull OrNode node) {
        super(node);
    }

    @Override
    @NonNull
    public BiFunction<Condition, Condition, Condition> logical() {
        return Condition::or;
    }

}
