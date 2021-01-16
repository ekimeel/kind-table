package kind.table.cols;

import java.io.Serializable;

public class LngColumn extends NumberColumn<Long> implements Serializable {

    /**
     * returns a new [[LngColumn]] with the provided name
     *
     * @param name a valid name
     * @return a new [[LngColumn]] with the provided name
     */
    public static LngColumn of(String name) {
        return new LngColumn(name);
    }

    public LngColumn(String name) {
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
        return new LngColumn(getName());
    }
}
