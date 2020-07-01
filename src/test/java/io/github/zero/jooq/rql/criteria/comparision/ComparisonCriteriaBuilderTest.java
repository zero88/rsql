package io.github.zero.jooq.rql.criteria.comparision;

import java.util.Arrays;
import java.util.Collections;

import org.jooq.Condition;
import org.junit.Assert;
import org.junit.Test;

import io.github.zero.jooq.rql.Tables;
import io.github.zero.jooq.rql.criteria.CriteriaBuilder;
import io.github.zero.jooq.rql.criteria.CriteriaBuilderFactory;
import io.github.zero.utils.Strings;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.RSQLOperators;

public class ComparisonCriteriaBuilderTest {

    @Test
    public void test_equal_node() {
        final ComparisonNode node = new ComparisonNode(RSQLOperators.EQUAL, Tables.ALL_DATA_TYPE.F_PERIOD.getName(),
                                                       Collections.singletonList("abc"));
        final CriteriaBuilder builder = CriteriaBuilderFactory.DEFAULT.create(node);
        Assert.assertTrue(builder instanceof EqualBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        Assert.assertEquals("\"PUBLIC\".\"ALL_DATA_TYPE\".\"F_PERIOD\" = 'abc'", condition.toString());
    }

    @Test
    public void test_not_equal_node() {
        final ComparisonNode node = new ComparisonNode(RSQLOperators.NOT_EQUAL, Tables.ALL_DATA_TYPE.F_PERIOD.getName(),
                                                       Collections.singletonList("abc"));
        final CriteriaBuilder builder = CriteriaBuilderFactory.DEFAULT.create(node);
        Assert.assertTrue(builder instanceof NotEqualBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        Assert.assertEquals("\"PUBLIC\".\"ALL_DATA_TYPE\".\"F_PERIOD\" <> 'abc'", condition.toString());
    }

    @Test
    public void test_greater_than_node() {
        final ComparisonNode node = new ComparisonNode(RSQLOperators.GREATER_THAN,
                                                       Tables.ALL_DATA_TYPE.F_REAL.getName(),
                                                       Collections.singletonList("5.5"));
        final CriteriaBuilder builder = CriteriaBuilderFactory.DEFAULT.create(node);
        Assert.assertTrue(builder instanceof GreaterThanBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        Assert.assertEquals("\"PUBLIC\".\"ALL_DATA_TYPE\".\"F_REAL\" > 5.5", condition.toString());
    }

    @Test
    public void test_greater_than_or_equal_node() {
        final ComparisonNode node = new ComparisonNode(RSQLOperators.GREATER_THAN_OR_EQUAL,
                                                       Tables.ALL_DATA_TYPE.F_INT.getName(),
                                                       Collections.singletonList("5"));
        final CriteriaBuilder builder = CriteriaBuilderFactory.DEFAULT.create(node);
        Assert.assertTrue(builder instanceof GreaterThanOrEqualBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        Assert.assertEquals("\"PUBLIC\".\"ALL_DATA_TYPE\".\"F_INT\" >= 5", condition.toString());
    }

    @Test
    public void test_less_than_node() {
        final ComparisonNode node = new ComparisonNode(RSQLOperators.LESS_THAN, Tables.ALL_DATA_TYPE.F_DOUBLE.getName(),
                                                       Collections.singletonList("23.54326"));
        final CriteriaBuilder builder = CriteriaBuilderFactory.DEFAULT.create(node);
        Assert.assertTrue(builder instanceof LessThanBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        Assert.assertEquals("\"PUBLIC\".\"ALL_DATA_TYPE\".\"F_DOUBLE\" < 23.54326", condition.toString());
    }

    @Test
    public void test_less_than_or_equal_node() {
        final ComparisonNode node = new ComparisonNode(RSQLOperators.LESS_THAN_OR_EQUAL,
                                                       Tables.ALL_DATA_TYPE.F_DECIMAL.getName(),
                                                       Collections.singletonList("55.24681579"));
        final CriteriaBuilder builder = CriteriaBuilderFactory.DEFAULT.create(node);
        Assert.assertTrue(builder instanceof LessThanOrEqualBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        Assert.assertEquals("\"PUBLIC\".\"ALL_DATA_TYPE\".\"F_DECIMAL\" <= 55.24681579", condition.toString());
    }

    @Test
    public void test_in_node() {
        final ComparisonNode node = new ComparisonNode(RSQLOperators.IN, Tables.ALL_DATA_TYPE.ID.getName(),
                                                       Arrays.asList("5", "7", "10"));
        final CriteriaBuilder builder = CriteriaBuilderFactory.DEFAULT.create(node);
        Assert.assertTrue(builder instanceof InBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        Assert.assertEquals("\"PUBLIC\".\"ALL_DATA_TYPE\".\"ID\" in ( 5, 7, 10 )",
                            Strings.optimizeMultipleSpace(condition.toString()));
    }

    @Test
    public void test_not_in_node() {
        final ComparisonNode node = new ComparisonNode(RSQLOperators.NOT_IN, Tables.ALL_DATA_TYPE.ID.getName(),
                                                       Arrays.asList("5", "7", "10"));
        final CriteriaBuilder builder = CriteriaBuilderFactory.DEFAULT.create(node);
        Assert.assertTrue(builder instanceof NotInBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        Assert.assertEquals("\"PUBLIC\".\"ALL_DATA_TYPE\".\"ID\" not in ( 5, 7, 10 )",
                            Strings.optimizeMultipleSpace(condition.toString()));
    }

    @Test
    public void test_between_node() {
        final ComparisonNode node = new ComparisonNode(BetweenBuilder.OPERATOR, Tables.ALL_DATA_TYPE.F_DATE.getName(),
                                                       Arrays.asList("2020-04-05T08:00:00Z", "2020-04-08T08:00:00Z"));
        final CriteriaBuilder builder = CriteriaBuilderFactory.DEFAULT.create(node);
        Assert.assertTrue(builder instanceof BetweenBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        Assert.assertEquals(
            "\"PUBLIC\".\"ALL_DATA_TYPE\".\"F_DATE\" between timestamp '2020-04-05 15:00:00.0' and timestamp " +
            "'2020-04-08 15:00:00.0'", Strings.optimizeMultipleSpace(condition.toString()));
    }

    @Test
    public void test_exists_node() {
        final ComparisonNode node = new ComparisonNode(ExistsBuilder.OPERATOR, Tables.ALL_DATA_TYPE.F_PERIOD.getName(),
                                                       Collections.singletonList("t"));
        final CriteriaBuilder builder = CriteriaBuilderFactory.DEFAULT.create(node);
        Assert.assertTrue(builder instanceof ExistsBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        Assert.assertEquals("\"PUBLIC\".\"ALL_DATA_TYPE\".\"F_PERIOD\" is not null",
                            Strings.optimizeMultipleSpace(condition.toString()));
    }

    @Test
    public void test_non_exists_node() {
        final ComparisonNode node = new ComparisonNode(NonExistsBuilder.OPERATOR,
                                                       Tables.ALL_DATA_TYPE.F_DURATION.getName(),
                                                       Collections.singletonList("t"));
        final CriteriaBuilder builder = CriteriaBuilderFactory.DEFAULT.create(node);
        Assert.assertTrue(builder instanceof NonExistsBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        Assert.assertEquals("\"PUBLIC\".\"ALL_DATA_TYPE\".\"F_DURATION\" is null",
                            Strings.optimizeMultipleSpace(condition.toString()));
    }

    @Test
    public void test_nullable_node() {
        final ComparisonNode node = new ComparisonNode(NullableBuilder.OPERATOR,
                                                       Tables.ALL_DATA_TYPE.F_VALUE_JSON.getName(),
                                                       Collections.singletonList("t"));
        final CriteriaBuilder builder = CriteriaBuilderFactory.DEFAULT.create(node);
        Assert.assertTrue(builder instanceof NullableBuilder);
        final Condition condition = builder.build(Tables.ALL_DATA_TYPE);
        Assert.assertEquals("( \"PUBLIC\".\"ALL_DATA_TYPE\".\"F_VALUE_JSON\" is null or \"PUBLIC\".\"ALL_DATA_TYPE\"" +
                            ".\"F_VALUE_JSON\" = 't' )", Strings.optimizeMultipleSpace(condition.toString()));
    }

}