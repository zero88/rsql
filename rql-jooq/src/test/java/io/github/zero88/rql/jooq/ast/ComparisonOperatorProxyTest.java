package io.github.zero88.rql.jooq.ast;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import io.github.zero88.rql.parser.ast.ComparisonOperatorProxy;

public class ComparisonOperatorProxyTest {

    @Test
    public void print() {
        ComparisonOperatorProxy.operators()
                               .forEach(o -> System.out.println(Arrays.toString(o.operator().getSymbols())));
    }

}