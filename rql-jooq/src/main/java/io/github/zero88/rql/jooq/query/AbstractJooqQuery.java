package io.github.zero88.rql.jooq.query;

import org.jooq.DSLContext;

import io.github.zero88.rql.jooq.AbstractJooqRqlFacade;
import io.github.zero88.rql.jooq.JooqRqlParser;
import io.github.zero88.rql.jooq.JooqRqlQuery;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@Accessors(fluent = true)
public abstract class AbstractJooqQuery<R, T, C> extends AbstractJooqRqlFacade implements JooqRqlQuery<R, T, C> {

    @NonNull
    private final DSLContext dsl;
    @NonNull
    @Default
    private final JooqRqlParser parser = JooqRqlParser.DEFAULT;

}
