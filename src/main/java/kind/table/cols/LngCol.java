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
    public Long cast(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Number) {
            return ((Number)value).longValue();
        }

        return (Long)value;
    }

    @Override
    public Col copy() {
        return new LngCol(getName(), getIndex());
    }
}