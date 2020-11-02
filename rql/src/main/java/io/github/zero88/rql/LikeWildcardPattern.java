package io.github.zero88.rql;

import lombok.NonNull;

/**
 * The interface Like wildcard pattern
 *
 * @see <a href="https://www.jcp.org/en/jsr/detail?id=317">JSR-317. Section 4.6.10 Like Expressions</a>
 * @since 1.0.0
 */
public interface LikeWildcardPattern {

    LikeWildcardPattern DEFAULT = new LikeWildcardPattern() {};

    /**
     * Stands for any sequence of characters (including the empty sequence)
     *
     * @return the sequence pattern
     */
    default @NonNull char zeroOrMore() {
        return '*';
    }

    /**
     * Stands for any single character.
     *
     * @return the single pattern
     */
    default @NonNull char single() {
        return '?';
    }

}
