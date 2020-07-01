package io.github.zero.jooq.rql;

import cz.jirutka.rsql.parser.ast.RSQLVisitor;

/**
 * The interface jOOQ RQL visitor.
 *
 * @param <R> Type of {@code Visitor Result}
 * @param <C> Type of {@code Visitor Context}
 * @see RSQLVisitor
 * @since 1.0.0
 */
public interface JooqRqlVisitor<R, C> extends RSQLVisitor<R, C>, JooqRqlFacade, HasLog {}
