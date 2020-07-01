package io.github.zero.jooq.rql.query;

import java.util.Optional;

import org.jooq.DSLContext;

import io.github.zero.jooq.rql.AbstractJooqRqlFacade;
import io.github.zero.jooq.rql.JooqRqlParser;
import io.github.zero.jooq.rql.JooqRqlQuery;

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
