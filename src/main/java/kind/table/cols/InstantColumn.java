package kind.table.cols;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

public class InstantColumn extends Column<Instant> implements Serializable {
    /**
     * returns a new [[InstantColumn]] with the provided name
     *
     * @param name a valid name
     * @return a new [[InstantColumn]] with the provided name
     */
    public static InstantColumn of(String name) {
        return new InstantColumn(name);
    }

    public InstantColumn(String name) {
        super(name);
    }
    public InstantColumn(String name, int index) {
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
    public Instant cast (Object value) {
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
    public Column copy () {
        return new InstantColumn(getName(), getIndex());
    }
}