package kind.table.cols;

import java.io.Serializable;

public class LongColumn extends Column<Long> implements Serializable {

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
