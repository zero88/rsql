package io.github.zero.jooq.rql.criteria;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import io.github.zero.jooq.rql.criteria.comparision.ComparisonCriteriaBuilder;
import io.github.zero.jooq.rql.criteria.logical.AndNodeCriteriaBuilder;
import io.github.zero.jooq.rql.criteria.logical.OrNodeCriteriaBuilder;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.OrNode;
import cz.jirutka.rsql.parser.ast.RSQLOperators;

public class CriteriaBuilderFactoryTest {

    @Test
    public void test_comparision_node() {
        final CriteriaBuilder builder = CriteriaBuilderFactory.DEFAULT.create(
            new ComparisonNode(RSQLOperators.EQUAL, "test", Collections.singletonList("abc")));
        Assert.assertTrue(builder instanceof ComparisonCriteriaBuilder);
    }

    @Test
    public void test_and_node() {
        final CriteriaBuilder builder = CriteriaBuilderFactory.DEFAULT.create(new AndNode(
            Collections.singletonList(new ComparisonNode(RSQLOperators.IN, "test", Arrays.asList("abc", "xyz")))));
        Assert.assertTrue(builder instanceof AndNodeCriteriaBuilder);
    }

    @Test
    public void test_or_node() {
        final CriteriaBuilder builder = CriteriaBuilderFactory.DEFAULT.create(new OrNode(
            Collections.singletonList(new ComparisonNode(RSQLOperators.IN, "test", Arrays.asList("abc", "xyz")))));
        Assert.assertTrue(builder instanceof OrNodeCriteriaBuilder);
    }

}