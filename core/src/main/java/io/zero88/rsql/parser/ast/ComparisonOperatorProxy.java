package io.zero88.rsql.parser.ast;

import java.util.Set;
import java.util.stream.Collectors;

import io.github.zero88.repl.ReflectionField;

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
@FunctionalInterface
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
    ComparisonOperatorProxy BETWEEN = () -> new ComparisonOperator("=between=", true);
    /**
     * The constant EXISTS.
     */
    ComparisonOperatorProxy EXISTS = () -> new ComparisonOperator("=exists=", "=nn=");
    /**
     * The constant NON_EXISTS.
     */
    ComparisonOperatorProxy NON_EXISTS = () -> new ComparisonOperator("=null=", "=isn=");
    /**
     * The constant NULLABLE.
     */
    ComparisonOperatorProxy NULLABLE = () -> new ComparisonOperator("=nullable=");

    ComparisonOperatorProxy LIKE = () -> new ComparisonOperator("=like=");

    ComparisonOperatorProxy NOT_LIKE = () -> new ComparisonOperator("=nk=", "=unlike=");

    /**
     * Get set of default comparison operators.
     *
     * @return the default comparison operators
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
