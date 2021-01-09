package kind.table.cols;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

public class DateColumn extends Column<Date> implements Serializable {

    public DateColumn(String name) {
        super(name);
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
    public Column copy() {
        return new DateColumn(getName());
    }

}
