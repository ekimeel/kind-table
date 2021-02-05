package kind.table.cols;

import java.io.Serializable;

/**
 * The type Lng col.
 */
public final class LngCol extends NumCol<Long> implements Serializable {

    /**
     * Of lng col.
     *
     * @param name the name
     * @return the lng col
     */
    public static LngCol of(String name) {
        return new LngCol(name);
    }

    /**
     * Instantiates a new Lng col.
     *
     * @param name the name
     */
    public LngCol(String name) {
        super(name);
    }

    /**
     * Instantiates a new Lng col.
     *
     * @param name  the name
     * @param index the index
     */
    public LngCol(String name, int index) {
        super(name, index);
    }

    @Override
    public Long convert(Object value, String format) {
        if (value == null) {
            return null;
        } else if (value instanceof Number) {
            return ((Number)value).longValue();
        } else if (value instanceof String) {
            final String str = (String)value;
            return (str.equals(NULL_STRING))? null : Long.parseLong(str);
        }

        return (Long)value;
    }

    @Override
    public Col copy() {
        return new LngCol(getName(), getIndex());
    }
}
