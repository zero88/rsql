package io.github.zero.jooq.rql;

import java.util.Optional;

import io.github.zero.jooq.rql.criteria.CriteriaBuilderFactory;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@Accessors(fluent = true)
public abstract class AbstractJooqRqlFacade implements JooqRqlFacade {

    private final QueryContext queryContext;
    private final CriteriaBuilderFactory criteriaBuilderFactory;

    @Override
    public @NonNull QueryContext queryContext() {
        return Optional.ofNullable(queryContext).orElseGet(JooqRqlFacade.super::queryContext);
    }

    @Override
    public @NonNull CriteriaBuilderFactory criteriaBuilderFactory() {
        return Optional.ofNullable(criteriaBuilderFactory).orElseGet(JooqRqlFacade.super::criteriaBuilderFactory);
    }

}
