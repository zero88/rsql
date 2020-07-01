package io.github.zero.jooq.rql.criteria.logical;

import java.util.function.BiFunction;

import org.jooq.Condition;

import io.github.zero.jooq.rql.criteria.AbstractCriteriaBuilder;

import cz.jirutka.rsql.parser.ast.OrNode;
import lombok.NonNull;

public final class OrNodeCriteriaBuilder extends AbstractCriteriaBuilder<OrNode>
    implements LogicalCriteriaBuilder<OrNode> {

    public OrNodeCriteriaBuilder(@NonNull OrNode node) {
        super(node);
    }

    @Override
    @NonNull
    public BiFunction<Condition, Condition, Condition> logical() {
        return Condition::or;
    }

}
