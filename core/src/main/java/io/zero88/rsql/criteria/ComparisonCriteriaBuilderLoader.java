package io.zero88.rsql.criteria;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import io.github.zero88.repl.ReflectionClass;
import io.github.zero88.utils.ServiceHelper;
import io.zero88.rsql.parser.ast.ComparisonOperatorProxy;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;

@SuppressWarnings({"unchecked", "rawtypes"})
public abstract class ComparisonCriteriaBuilderLoader {

    private final Map<ComparisonOperator, Class<? extends ComparisonCriteriaBuilder>> map;

    protected ComparisonCriteriaBuilderLoader() {
        this.map = ServiceHelper.loadFactories(serviceClass())
                                .stream()
                                .collect(Collectors.toMap(b -> b.operator().operator(),
                                                          ComparisonCriteriaBuilder::getClass));
    }

    public <T extends ComparisonOperatorProxy> ComparisonCriteriaBuilder<T> get(ComparisonOperator operator) {
        return Optional.ofNullable(map.get(operator))
                       .map(ReflectionClass::createObject)
                       .orElseThrow(() -> new IllegalArgumentException(
                           "Not found criteria builder for [" + operator.getSymbol() + "]"));
    }

    public Set<ComparisonOperator> operators() {
        return map.keySet();
    }

    protected abstract Class<? extends ComparisonCriteriaBuilder> serviceClass();

}
