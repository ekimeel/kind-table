package kind.table.cols;

import java.io.Serializable;
import java.util.Date;

public final class DateCol extends Col<Date> implements Serializable {

    /**
     * returns a new [[DateCol]] with the provided name
     *
     * @param name a valid name
     * @return a new [[DateCol]] with the provided name
     */
    public static DateCol of(String name) {
        return new DateCol(name);
    }

    public DateCol(String name) {
        super(name);
    }

    public DateCol(String name, int index) {
        super(name, index);
    }

    /**
     *
     * Supports the following class types:
     * <ul>
     *     <li>java.util.Date</li>
     *     <li>java.sql.Date</li>
     *     <li>java.lang.Number</li>
     * </ul>
     * @param value The value to try to cast
     * @return
     */
    @Override
    public Date cast(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Number) {
            return new Date(((Number)value).longValue());
        } else if (value instanceof java.sql.Date) {
            return new Date(((java.sql.Date)value).getTime());
        }

        return (Date) value;
    }

    @Override
    public Col copy() {
        return new DateCol(getName(), getIndex());
    }

}
