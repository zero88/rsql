package io.github.zero.jooq.rql.criteria.comparision;

import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.TableLike;
import org.jooq.impl.DSL;

import io.github.zero.jooq.rql.ArgumentParser;
import io.github.zero.jooq.rql.FieldMapper;
import io.github.zero.jooq.rql.QueryContext;
import io.github.zero.jooq.rql.criteria.AbstractCriteriaBuilder;
import io.github.zero.jooq.rql.criteria.CriteriaBuilderFactory;
import io.github.zero.jooq.rql.parser.ast.ComparisonOperatorProxy;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public abstract class AbstractComparisionCriteriaBuilder extends AbstractCriteriaBuilder<ComparisonNode>
    implements ComparisonCriteriaBuilder<ComparisonOperatorProxy> {

    @NonNull
    private final ComparisonOperatorProxy operator;

    public AbstractComparisionCriteriaBuilder(@NonNull ComparisonNode node) {
        this(node, ComparisonOperatorProxy.asProxy(node.getOperator()));
    }

    protected AbstractComparisionCriteriaBuilder(@NonNull ComparisonNode node,
                                                 @NonNull ComparisonOperatorProxy operator) {
        super(node);
        this.operator = operator;
    }

    @Override
    public @NonNull Condition build(@NonNull TableLike table, @NonNull QueryContext queryContext,
                                    @NonNull CriteriaBuilderFactory factory) {
        final FieldMapper fieldMapper = queryContext.fieldMapper();
        final ArgumentParser parser = queryContext.argumentParser();
        return fieldMapper.get(table, node().getSelector())
                          .map(f -> compare(f, node().getArguments(), parser))
                          .orElse(DSL.trueCondition());
    }

    protected abstract @NonNull Condition compare(@NonNull Field field, @NonNull List<String> arguments,
                                                  @NonNull ArgumentParser parser);

}
