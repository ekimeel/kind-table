package kind.table.cols;

import java.io.Serializable;

public final class DoubleColumn extends NumberColumn<Double> implements Serializable {

    /**
     * returns a new [[DoubleColumn]] with the provided name
     *
     * @param name a valid name
     * @return a new [[DoubleColumn]] with the provided name
     */
    public static DoubleColumn of(String name) {
        return new DoubleColumn(name);
    }

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
