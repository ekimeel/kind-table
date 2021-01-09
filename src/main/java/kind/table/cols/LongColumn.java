package kind.table.cols;

import java.io.Serializable;

public class LongColumn extends NumberColumn<Long> implements Serializable {

    /**
     * returns a new [[LongColumn]] with the provided name
     *
     * @param name a valid name
     * @return a new [[LongColumn]] with the provided name
     */
    public static LongColumn of(String name) {
        return new LongColumn(name);
    }

    public LongColumn(String name) {
        super(name);
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
    public Column copy() {
        return new LongColumn(getName());
    }
}
