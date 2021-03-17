package io.zero88.rsql.jooq.ast;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import io.zero88.rsql.parser.ast.ComparisonOperatorProxy;

public class ComparisonOperatorProxyTest {

    @Test
    public void print() {
        ComparisonOperatorProxy.operators()
                               .forEach(o -> System.out.println(Arrays.toString(o.operator().getSymbols())));
    }

}