package io.github.zero.jooq.rql;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

import org.jooq.SelectFieldOrAsterisk;
import org.jooq.TableLike;
import org.jooq.impl.DSL;

import lombok.NonNull;

/**
 * The interface Field selector.
 *
 * @see SelectFieldOrAsterisk
 * @see TableLike
 * @since 1.0.0
 */
public interface FieldSelector extends Supplier<Collection<? extends SelectFieldOrAsterisk>> {

    /**
     * The constant DEFAULT.
     */
    FieldSelector DEFAULT = () -> Collections.singleton(DSL.asterisk());

    /**
     * Get fields
     *
     * @return collection fields
     */
    @NonNull Collection<? extends SelectFieldOrAsterisk> get();

}
