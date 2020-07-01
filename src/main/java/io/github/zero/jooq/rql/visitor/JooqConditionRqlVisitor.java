package io.github.zero.jooq.rql.visitor;

import org.jooq.Condition;
import org.jooq.TableLike;

import io.github.zero.jooq.rql.QueryContext;
import io.github.zero.jooq.rql.criteria.CriteriaBuilderFactory;

import lombok.NonNull;

/**
 * The interface jOOQ condition RQL visitor.
 *
 * @see Condition
 * @since 1.0.0
 */
public interface JooqConditionRqlVisitor extends JooqNoArgsRqlVisitor<Condition> {

    /**
     * Create jooq condition rql visitor.
     *
     * @param table the table
     * @return the jooq condition rql visitor
     * @see TableLike
     * @since 1.0.0
     */
    static JooqConditionRqlVisitor create(@NonNull TableLike table) {
        return DefaultJooqConditionRqlVisitor.builder().table(table).build();
    }

    /**
     * Create jooq condition rql visitor.
     *
     * @param table        the table
     * @param queryContext the query context
     * @return the jooq condition rql visitor
     * @see TableLike
     * @see QueryContext
     * @since 1.0.0
     */
    static JooqConditionRqlVisitor create(@NonNull TableLike table, QueryContext queryContext) {
        return DefaultJooqConditionRqlVisitor.builder().table(table).queryContext(queryContext).build();
    }

    /**
     * Create jooq condition rql visitor.
     *
     * @param table        the table
     * @param queryContext the query context
     * @param factory      the factory
     * @return the jooq condition rql visitor
     * @see TableLike
     * @see QueryContext
     * @see CriteriaBuilderFactory
     * @since 1.0.0
     */
    static JooqConditionRqlVisitor create(@NonNull TableLike table, QueryContext queryContext,
                                          CriteriaBuilderFactory factory) {
        return DefaultJooqConditionRqlVisitor.builder()
                                             .table(table)
                                             .queryContext(queryContext)
                                             .criteriaBuilderFactory(factory)
                                             .build();
    }

}
