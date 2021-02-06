package kind.table.cols;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The type Date col.
 */
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

    /**
     * Instantiates a new Date col.
     *
     * @param name the name
     */
    public DateCol(String name) {
        super(name);
    }

    /**
     * Instantiates a new Date col.
     *
     * @param name  the name
     * @param index the index
     */
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
     * @return The converted date
     */
    @Override
    public Date convert(Object value, String format) {
        if (value == null) {
            return null;
        } else if (value instanceof Number) {
            return new Date(((Number)value).longValue());
        } else if (value instanceof java.sql.Date) {
            return new Date(((java.sql.Date)value).getTime());
        } else if (value instanceof String) {
            try {
                return (format != null)?
                        new SimpleDateFormat(format).parse((String)value) :
                        SimpleDateFormat.getDateTimeInstance().parse((String)value);
            } catch (ParseException e) {
                throw new IllegalArgumentException(String.format("failed to convert [%s] to a date using format [%s]", value, format));
            }

        }

        //try a direct cast, good luck :)
        return (Date) value;
    }

    @Override
    public Col copy() {
        return new DateCol(getName(), getIndex());
    }

}
