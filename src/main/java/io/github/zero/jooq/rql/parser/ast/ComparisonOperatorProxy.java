package io.github.zero.jooq.rql.parser.ast;

import java.util.Set;
import java.util.stream.Collectors;

import io.github.zero.jooq.rql.criteria.comparision.BetweenBuilder;
import io.github.zero.jooq.rql.criteria.comparision.ExistsBuilder;
import io.github.zero.jooq.rql.criteria.comparision.NonExistsBuilder;
import io.github.zero.jooq.rql.criteria.comparision.NullableBuilder;
import io.github.zero.utils.Reflections.ReflectionField;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.RSQLOperators;
import lombok.NonNull;

/**
 * The interface Comparison operator proxy.
 * <p>
 * That helps to make more extension comparator or customize existence comparator
 *
 * @see ComparisonOperator
 * @since 1.0.0
 */
public interface ComparisonOperatorProxy {

    /**
     * The constant EQUAL.
     */
    ComparisonOperatorProxy EQUAL = () -> RSQLOperators.EQUAL;
    /**
     * The constant NOT_EQUAL.
     */
    ComparisonOperatorProxy NOT_EQUAL = () -> RSQLOperators.NOT_EQUAL;
    /**
     * The constant GREATER_THAN.
     */
    ComparisonOperatorProxy GREATER_THAN = () -> RSQLOperators.GREATER_THAN;
    /**
     * The constant GREATER_THAN_OR_EQUAL.
     */
    ComparisonOperatorProxy GREATER_THAN_OR_EQUAL = () -> RSQLOperators.GREATER_THAN_OR_EQUAL;
    /**
     * The constant LESS_THAN.
     */
    ComparisonOperatorProxy LESS_THAN = () -> RSQLOperators.LESS_THAN;
    /**
     * The constant LESS_THAN_OR_EQUAL.
     */
    ComparisonOperatorProxy LESS_THAN_OR_EQUAL = () -> RSQLOperators.LESS_THAN_OR_EQUAL;
    /**
     * The constant IN.
     */
    ComparisonOperatorProxy IN = () -> RSQLOperators.IN;
    /**
     * The constant NOT_IN.
     */
    ComparisonOperatorProxy NOT_IN = () -> RSQLOperators.NOT_IN;
    /**
     * The constant BETWEEN.
     */
    ComparisonOperatorProxy BETWEEN = () -> BetweenBuilder.OPERATOR;
    /**
     * The constant EXISTS.
     */
    ComparisonOperatorProxy EXISTS = () -> ExistsBuilder.OPERATOR;
    /**
     * The constant NON_EXISTS.
     */
    ComparisonOperatorProxy NON_EXISTS = () -> NonExistsBuilder.OPERATOR;
    /**
     * The constant NULLABLE.
     */
    ComparisonOperatorProxy NULLABLE = () -> NullableBuilder.OPERATOR;

    /**
     * Parse comparison operator to proxy comparison operator.
     *
     * @param operator the operator
     * @return the comparison operator proxy
     * @since 1.0.0
     */
    static @NonNull ComparisonOperatorProxy asProxy(@NonNull ComparisonOperator operator) {
        return ReflectionField.streamConstants(ComparisonOperatorProxy.class)
                              .filter(proxy -> proxy.operator().equals(operator))
                              .findFirst()
                              .orElseThrow(() -> new IllegalArgumentException("Unknown operation " + operator));
    }

    /**
     * Get set of default comparision operators.
     *
     * @return the default comparision operators
     * @since 1.0.0
     */
    static Set<ComparisonOperatorProxy> operators() {
        return ReflectionField.streamConstants(ComparisonOperatorProxy.class).collect(Collectors.toSet());
    }

    /**
     * Gets comparison operator.
     *
     * @return the comparison operator
     * @see ComparisonOperator
     * @since 1.0.0
     */
    @NonNull ComparisonOperator operator();

}
