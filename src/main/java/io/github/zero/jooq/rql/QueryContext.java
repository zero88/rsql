package io.github.zero.jooq.rql;

import lombok.NonNull;

/**
 * The interface Query context.
 *
 * @since 1.0.0
 */
public interface QueryContext {

    /**
     * The constant DEFAULT.
     */
    QueryContext DEFAULT = new QueryContext() {};

    /**
     * Like wildcard like wildcard pattern.
     *
     * @return the like wildcard pattern
     * @see LikeWildcardPattern
     * @since 1.0.0
     */
    default @NonNull LikeWildcardPattern likeWildcard() {
        return LikeWildcardPattern.DEFAULT;
    }

    /**
     * Field selector.
     *
     * @return the field selector
     * @see FieldSelector
     * @since 1.0.0
     */
    default @NonNull FieldSelector fieldSelector() {
        return FieldSelector.DEFAULT;
    }

    /**
     * Field mapper.
     *
     * @return the field mapper
     * @see FieldMapper
     * @since 1.0.0
     */
    default @NonNull FieldMapper fieldMapper() {
        return FieldMapper.DEFAULT;
    }

    /**
     * Argument parser.
     *
     * @return the argument parser
     * @see ArgumentParser
     * @since 1.0.0
     */
    default @NonNull ArgumentParser argumentParser() {
        return ArgumentParser.DEFAULT;
    }

}
