package io.zero88.jpa;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

/**
 * Sort option for queries.
 *
 * @since 1.0.0
 */
public interface Sortable extends Serializable {

    /**
     * Get orders collection.
     *
     * @return the order collection
     * @since 1.0.0
     */
    @NonNull Collection<Order> orders();

    /**
     * Get order.
     *
     * @param property the property
     * @return the order
     * @since 1.0.0
     */
    Order get(String property);

    /**
     * Is empty boolean.
     *
     * @return the boolean
     * @since 1.0.0
     */
    boolean isEmpty();

    /**
     * Represents for {@code Sort Direction}.
     *
     * @since 1.0.0
     */
    @Getter
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    enum Direction implements Serializable {
        /**
         * {@code ASC} direction.
         */
        ASC('+'),
        /**
         * {@code DESC} direction.
         */
        DESC('-');

        private final char symbol;

        /**
         * Parse direction.
         *
         * @param direction the direction
         * @return the direction
         * @since 1.0.0
         */
        public static Direction parse(String direction) {
            String d = Optional.ofNullable(direction).map(String::trim).orElse(null);
            if (Objects.isNull(d) || d.equals("")) {
                return ASC;
            }
            if (d.length() == 1) {
                return d.charAt(0) == DESC.symbol ? DESC : ASC;
            }
            return Stream.of(Direction.values()).filter(t -> t.name().equalsIgnoreCase(d)).findFirst().orElse(null);
        }

        public static Direction parse(char direction) {
            return direction == DESC.symbol ? DESC : ASC;
        }

        public boolean isASC() {
            return ASC == this;
        }

        public boolean isDESC() {
            return DESC == this;
        }
    }


    /**
     * Represents for Order.
     *
     * @since 1.0.0
     */
    @Getter
    @Accessors(fluent = true)
    @ToString
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @JsonSerialize(using = OrderSerializer.class)
    class Order implements Serializable {

        @NonNull
        private final String property;
        @NonNull
        private final Direction direction;

        /**
         * By asc order.
         *
         * @param property the property
         * @return the order
         * @since 1.0.0
         */
        public static Order byASC(@NonNull String property) {
            return by(property, Direction.ASC);
        }

        /**
         * By desc order.
         *
         * @param property the property
         * @return the order
         * @since 1.0.0
         */
        public static Order byDESC(@NonNull String property) {
            return by(property, Direction.DESC);
        }

        /**
         * By order.
         *
         * @param property  the property
         * @param direction the direction
         * @return the order
         * @since 1.0.0
         */
        public static Order by(@NonNull String property, Direction direction) {
            return new Order(property, Optional.ofNullable(direction).orElse(Direction.ASC));
        }

        /**
         * By order.
         *
         * @param property  the property
         * @param direction the direction
         * @return the order
         * @since 1.0.0
         */
        public static Order by(@NonNull @JsonProperty("property") String property,
                               @JsonProperty("direction") String direction) {
            return by(property, Direction.parse(direction));
        }

        @JsonCreator
        public static Order by(@NonNull Map<String, String> properties) {
            final Entry<String, String> val = properties.entrySet()
                                                        .stream()
                                                        .findFirst()
                                                        .orElseThrow(() -> new RuntimeException("Empty value"));
            return by(val.getKey(), Direction.parse(val.getValue()));
        }

    }


    class OrderSerializer extends JsonSerializer<Order> {

        @Override
        public void serialize(Order value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            gen.writeObjectField(value.property(), value.direction());
            gen.writeEndObject();
        }

    }

}
