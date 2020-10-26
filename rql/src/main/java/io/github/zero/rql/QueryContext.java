package io.github.zero.rql;

import lombok.NonNull;

/**
 * The interface Query context.
 *
 * @since 1.0.0
 */
public interface QueryContext {

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
    @NonNull FieldSelector fieldSelector();

    /**
     * Field mapper.
     *
     * @return the field mapper
     * @see FieldMapper
     * @since 1.0.0
     */
    @NonNull FieldMapper fieldMapper();

    /**
     * Argument parser.
     *
     * @return the argument parser
     * @see ArgumentParser
     * @since 1.0.0
     */
    @NonNull ArgumentParser argumentParser();

}
