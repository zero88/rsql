package io.github.zero88.rql.parser.ast;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;

public class BuiltinComparisonOperator {

    public static final ComparisonOperator BETWEEN = new ComparisonOperator("=between=", true);
    public static final ComparisonOperator EXISTS = new ComparisonOperator("=exists=", "=nn=");
    public static final ComparisonOperator NON_EXISTS = new ComparisonOperator("=null=", "=isn=");
    public static final ComparisonOperator NULLABLE = new ComparisonOperator("=nullable=");
    public static final ComparisonOperator NOT_LIKE = new ComparisonOperator("=nk=", "=unlike=");
    public static final ComparisonOperator LIKE = new ComparisonOperator("=like=");

}
