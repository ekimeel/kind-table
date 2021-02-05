package kind.table.cols;

import java.io.Serializable;

public final class LngCol extends NumCol<Long> implements Serializable {

    /**
     * returns a new [[LngCol]] with the provided name
     *
     * @param name a valid name
     * @return a new [[LngCol]] with the provided name
     */
    public static LngCol of(String name) {
        return new LngCol(name);
    }

    public LngCol(String name) {
        super(name);
    }
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
