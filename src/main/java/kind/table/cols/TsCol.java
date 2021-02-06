package kind.table.cols;

import java.io.Serializable;
import java.time.Instant;

/**
 * The type Ts col.
 */
public final class TsCol extends Col<Instant> implements Serializable {
    /**
     * returns a new [[TsCol]] with the provided name
     *
     * @param name a valid name
     * @return a new [[TsCol]] with the provided name
     */
    public static TsCol of(String name) {
        return new TsCol(name);
    }

    /**
     * Instantiates a new Ts col.
     *
     * @param name the name
     */
    public TsCol(String name) {
        super(name);
    }

    /**
     * Instantiates a new Ts col.
     *
     * @param name  the name
     * @param index the index
     */
    public TsCol(String name, int index) {
        super(name, index);
    }

    /**
     *
     * Supports the following class types:
     * <ul>
     *     <li>java.util.Instant</li>
     *     <li>java.util.Date</li>
     *     <li>java.sql.Date</li>
     *     <li>java.lang.Number</li>
     * </ul>
     * @param value The value to try to cast
     * @return
     */
    @Override
    public Instant convert(Object value, String format) {
        if (value == null) {
            return null;
        } else if (value instanceof Instant) {
            return (Instant) value;
        } else if (value instanceof java.util.Date) {
            return Instant.ofEpochSecond(((java.util.Date)value).getTime());
        } else if (value instanceof java.sql.Date) {
            return Instant.ofEpochSecond(((java.sql.Date)value).getTime());
        } else if (value instanceof Number) {
            return Instant.ofEpochSecond(((Number)value).longValue());
        }

        return (Instant) value;
    }

    @Override
    public Col copy () {
        return new TsCol(getName(), getIndex());
    }
}