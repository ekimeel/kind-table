package kind.table.cols;

import java.io.Serializable;

public final class DoubleColumn extends Column<Double> implements Serializable {

    public DoubleColumn(String name) {
        super(name);
    }

    @Override
    public Double cast(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Number) {
            return ((Number)value).doubleValue();
        }

        return (Double) value;
    }

    @Override
    public Column copy() {
        return new DoubleColumn(getName());
    }
}
