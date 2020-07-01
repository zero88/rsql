package io.github.zero.jooq.rql.criteria.comparision;

import java.util.Collections;
import java.util.Map;

import io.github.zero.jooq.rql.criteria.CriteriaBuilder;
import io.github.zero.jooq.rql.parser.ast.ComparisonOperatorProxy;
import io.github.zero.utils.Reflections.ReflectionClass;
import io.github.zero.utils.Reflections.ReflectionField;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import lombok.NonNull;

/**
 * The interface Comparison criteria builder.
 *
 * @param <T> Type of {@code ComparisonOperatorProxy}
 * @see ComparisonNode
 * @see ComparisonOperatorProxy
 * @see CriteriaBuilder
 * @since 1.0.0
 */
public interface ComparisonCriteriaBuilder<T extends ComparisonOperatorProxy> extends CriteriaBuilder<ComparisonNode> {

    /**
     * Create comparison criteria builder.
     *
     * @param node the node
     * @return the comparison criteria builder
     * @since 1.0.0
     */
    //TODO change to reflection solution
    @SuppressWarnings("unchecked")
    static ComparisonCriteriaBuilder<ComparisonOperatorProxy> create(@NonNull ComparisonNode node) {
        final ComparisonOperator operator = node.getOperator();
        final Map<Class, Object> input = Collections.singletonMap(ComparisonNode.class, node);
        return ReflectionClass.stream(ComparisonCriteriaBuilder.class.getPackage().getName(),
                                      ComparisonCriteriaBuilder.class, ReflectionClass.publicClass())
                              .filter(clazz -> operator.equals(ReflectionField.constantByName(clazz, "OPERATOR")))
                              .map(clazz -> ReflectionClass.createObject(clazz, input))
                              .findFirst()
                              .orElseThrow(() -> new UnsupportedOperationException("Unknown operator " + operator));
    }

    @NonNull ComparisonNode node();

    /**
     * Comparision operator.
     *
     * @return the comparision operator
     * @since 1.0.0
     */
    @NonNull T operator();

}
