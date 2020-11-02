package io.github.zero88.rql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The interface Has log.
 *
 * @since 1.0.0
 */
public interface HasLog {

    /**
     * Gets logger.
     *
     * @return the logger
     * @since 1.0.0
     */
    default Logger log() {
        return LoggerFactory.getLogger(getClass());
    }

}
