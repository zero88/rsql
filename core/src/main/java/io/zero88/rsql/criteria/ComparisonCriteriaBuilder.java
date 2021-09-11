package io.zero88.rsql.criteria;

import io.github.zero88.repl.Arguments;
import io.github.zero88.repl.ReflectionClass;
import io.github.zero88.repl.ReflectionElement;
import io.github.zero88.repl.ReflectionField;
import io.zero88.rsql.parser.ast.ComparisonOperatorProxy;

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
     * Create comparison criteria builder by reflection.
     * <p>
     * It is equivalent to invoke {@link #create(ComparisonNode, Class, String)} with the implementation classes is same
     * Java package with parentClass.
     *
     * @param node        the comparison node
     * @param parentClass the parent builder class
     * @param <B>         Type of {@link ComparisonCriteriaBuilder}
     * @return the comparison criteria builder
     */
    static <B extends ComparisonCriteriaBuilder<ComparisonOperatorProxy>> B create(@NonNull ComparisonNode node,
                                                                                   @NonNull Class<B> parentClass) {
        return create(node, parentClass, parentClass.getPackage().getName());
    }

    /**
     * Create comparison criteria builder by reflection.
     *
     * @param node        the comparison node
     * @param parentClass the parent builder class
     * @param packageName package name
     * @param <B>         Type of {@link ComparisonCriteriaBuilder}
     * @return the comparison criteria builder
     */
    static <B extends ComparisonCriteriaBuilder<ComparisonOperatorProxy>> B create(@NonNull ComparisonNode node,
                                                                                   @NonNull Class<B> parentClass,
                                                                                   @NonNull String packageName) {
        final ComparisonOperator operator = node.getOperator();
        final Arguments input = new Arguments().put(ComparisonNode.class, node);
        return ReflectionClass.stream(packageName, parentClass, ReflectionElement.isPublicClass())
                              .filter(clazz -> operator.equals(ReflectionField.constantByName(clazz, "OPERATOR")))
                              .map(clazz -> ReflectionClass.createObject(clazz, input))
                              .findFirst()
                              .orElseThrow(() -> new UnsupportedOperationException("Unknown operator " + operator));
    }

    @NonNull ComparisonNode node();

    /**
     * Comparison operator.
     *
     * @return the comparison operator
     * @since 1.0.0
     */
    @NonNull T operator();

}
