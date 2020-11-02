package io.github.zero88.rql.jooq.criteria;

import io.github.zero88.rql.criteria.ComparisonCriteriaBuilder;
import io.github.zero88.rql.criteria.CriteriaBuilder;
import io.github.zero88.rql.criteria.CriteriaBuilderFactory;
import io.github.zero88.rql.jooq.criteria.logical.AndNodeCriteriaBuilder;
import io.github.zero88.rql.jooq.criteria.logical.OrNodeCriteriaBuilder;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.OrNode;
import lombok.NonNull;

/**
 * The interface Criteria builder factory.
 *
 * @since 1.0.0
 */
public interface JooqCriteriaBuilderFactory extends CriteriaBuilderFactory<Node, JooqCriteriaBuilder<Node>> {

    /**
     * The constant DEFAULT.
     */
    JooqCriteriaBuilderFactory DEFAULT = new JooqCriteriaBuilderFactory() {};

    @Override
    default @NonNull CriteriaBuilder<AndNode> andNodeCriteriaBuilder(@NonNull AndNode node) {
        return new AndNodeCriteriaBuilder(node);
    }

    @Override
    default @NonNull CriteriaBuilder<OrNode> orNodeCriteriaBuilder(@NonNull OrNode node) {
        return new OrNodeCriteriaBuilder(node);
    }

    @Override
    default @NonNull CriteriaBuilder<ComparisonNode> comparisonNodeCriteriaBuilder(@NonNull ComparisonNode node) {
        final Class<JooqComparisonCriteriaBuilder> parent = JooqComparisonCriteriaBuilder.class;
        return ComparisonCriteriaBuilder.create(node, parent, parent.getPackage().getName() + ".comparison");
    }

}
