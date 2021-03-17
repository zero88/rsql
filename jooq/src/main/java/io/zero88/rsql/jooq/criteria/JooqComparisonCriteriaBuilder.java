package io.zero88.rsql.jooq.criteria;

import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.TableLike;
import org.jooq.impl.DSL;

import io.zero88.rsql.criteria.AbstractCriteriaBuilder;
import io.zero88.rsql.criteria.ComparisonCriteriaBuilder;
import io.zero88.rsql.jooq.JooqArgumentParser;
import io.zero88.rsql.jooq.JooqFieldMapper;
import io.zero88.rsql.jooq.JooqQueryContext;
import io.zero88.rsql.parser.ast.ComparisonOperatorProxy;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public abstract class JooqComparisonCriteriaBuilder extends AbstractCriteriaBuilder<ComparisonNode>
    implements JooqCriteriaBuilder<ComparisonNode>, ComparisonCriteriaBuilder<ComparisonOperatorProxy> {

    @NonNull
    private final ComparisonOperatorProxy operator;

    public JooqComparisonCriteriaBuilder(@NonNull ComparisonNode node) {
        this(node, ComparisonOperatorProxy.asProxy(node.getOperator()));
    }

    protected JooqComparisonCriteriaBuilder(@NonNull ComparisonNode node,
                                            @NonNull ComparisonOperatorProxy operator) {
        super(node);
        this.operator = operator;
    }

    @Override
    public @NonNull Condition build(@NonNull TableLike table, @NonNull JooqQueryContext queryContext,
                                    @NonNull JooqCriteriaBuilderFactory factory) {
        final JooqFieldMapper fieldMapper = queryContext.fieldMapper();
        final JooqArgumentParser parser = queryContext.argumentParser();
        return fieldMapper.get(table, node().getSelector())
                          .map(f -> compare(f, node().getArguments(), parser))
                          .orElse(DSL.trueCondition());
    }

    protected abstract @NonNull Condition compare(@NonNull Field field, @NonNull List<String> arguments,
                                                  @NonNull JooqArgumentParser parser);

}
