package io.github.zero88.rql.jooq.query;

import java.util.Optional;

import org.jooq.DSLContext;

import io.github.zero88.rql.jooq.AbstractJooqRqlFacade;
import io.github.zero88.rql.jooq.JooqRqlParser;
import io.github.zero88.rql.jooq.JooqRqlQuery;

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
    private final JooqRqlParser parser;


    public static abstract class AbstractJooqQueryBuilder<R, T, C, C2 extends AbstractJooqQuery<R, T, C>,
                                                             B extends AbstractJooqQueryBuilder<R, T, C, C2, B>>
        extends AbstractJooqRqlFacade.AbstractJooqRqlFacadeBuilder<C2, B> {

        private @NonNull JooqRqlParser parser = JooqRqlParser.DEFAULT;

        public B parser(JooqRqlParser parser) {
            this.parser = Optional.ofNullable(parser).orElse(parser);
            return self();
        }

    }

}
