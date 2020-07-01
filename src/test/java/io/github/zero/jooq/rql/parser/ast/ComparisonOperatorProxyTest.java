package io.github.zero.jooq.rql.parser.ast;

import java.util.Arrays;

import org.junit.Test;

public class ComparisonOperatorProxyTest {

    @Test
    public void print() {
        ComparisonOperatorProxy.operators()
                               .forEach(o -> System.out.println(Arrays.toString(o.operator().getSymbols())));
    }

}