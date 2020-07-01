package io.github.zero.jooq.rql.criteria;

import java.util.Arrays;
import java.util.Collections;

import org.jooq.Condition;
import org.junit.Assert;
import org.junit.Test;

import io.github.zero.jooq.rql.Tables;
import io.github.zero.utils.Strings;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLOperators;

public class CriteriaBuilderTest {

    @Test
    public void test_and_node() {
        final ComparisonNode eqNode1 = new ComparisonNode(RSQLOperators.EQUAL, Tables.ALL_DATA_TYPE.F_STR.getName(),
                                                          Collections.singletonList("abc"));
        final ComparisonNode eqNode2 = new ComparisonNode(RSQLOperators.EQUAL, Tables.ALL_DATA_TYPE.F_BOOL.getName(),
                                                          Collections.singletonList("true"));
        final AndNode node = new AndNode(Arrays.asList(eqNode1, eqNode2));
        final Condition condition = CriteriaBuilderFactory.DEFAULT.create(node).build(Tables.ALL_DATA_TYPE);
        Assert.assertEquals("( 1 = 1 and \"PUBLIC\".\"ALL_DATA_TYPE\".\"F_STR\" = 'abc' and \"PUBLIC\"" +
                            ".\"ALL_DATA_TYPE\".\"F_BOOL\" = true )",
                            Strings.optimizeMultipleSpace(condition.toString()));
    }

    @Test
    public void test_or_node() {
        final ComparisonNode eqNode1 = new ComparisonNode(RSQLOperators.EQUAL,
                                                          Tables.ALL_DATA_TYPE.F_DURATION.getName(),
                                                          Collections.singletonList("abc"));
        final ComparisonNode eqNode2 = new ComparisonNode(RSQLOperators.EQUAL, Tables.ALL_DATA_TYPE.F_PERIOD.getName(),
                                                          Collections.singletonList("xyz"));
        final OrNode node = new OrNode(Arrays.asList(eqNode1, eqNode2));
        final Condition condition = CriteriaBuilderFactory.DEFAULT.create(node).build(Tables.ALL_DATA_TYPE);
        Assert.assertEquals("( ( 1 = 1 and \"PUBLIC\".\"ALL_DATA_TYPE\".\"F_DURATION\" = 'abc' ) or \"PUBLIC\"" +
                            ".\"ALL_DATA_TYPE\".\"F_PERIOD\" = 'xyz' )",
                            Strings.optimizeMultipleSpace(condition.toString()));
    }

    @Test
    public void test_combine_logical_node() {
        final ComparisonNode eqNode1 = new ComparisonNode(RSQLOperators.EQUAL, Tables.ALL_DATA_TYPE.F_STR.getName(),
                                                          Collections.singletonList("abc"));
        final ComparisonNode eqNode2 = new ComparisonNode(RSQLOperators.EQUAL, Tables.ALL_DATA_TYPE.F_BOOL.getName(),
                                                          Collections.singletonList("true"));
        final ComparisonNode eqNode3 = new ComparisonNode(RSQLOperators.EQUAL,
                                                          Tables.ALL_DATA_TYPE.F_DURATION.getName(),
                                                          Collections.singletonList("def"));
        final ComparisonNode eqNode4 = new ComparisonNode(RSQLOperators.EQUAL, Tables.ALL_DATA_TYPE.F_PERIOD.getName(),
                                                          Collections.singletonList("xyz"));
        final OrNode orNode = new OrNode(Arrays.asList(eqNode3, eqNode4));
        final AndNode node = new AndNode(Arrays.asList(eqNode1, eqNode2, orNode));
        final Condition condition = CriteriaBuilderFactory.DEFAULT.create(node).build(Tables.ALL_DATA_TYPE);
        Assert.assertEquals("( 1 = 1 and \"PUBLIC\".\"ALL_DATA_TYPE\".\"F_STR\" = 'abc' and \"PUBLIC\"" +
                            ".\"ALL_DATA_TYPE\".\"F_BOOL\" = true and ( ( 1 = 1 and \"PUBLIC\".\"ALL_DATA_TYPE\"" +
                            ".\"F_DURATION\" = 'def' ) or \"PUBLIC\".\"ALL_DATA_TYPE\".\"F_PERIOD\" = 'xyz' ) )",
                            Strings.optimizeMultipleSpace(condition.toString()));
    }

}